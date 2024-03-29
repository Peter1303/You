package top.pdev.you.domain.service.activity.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.event.ActivityEvent;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.constant.ActivityRule;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.TimeCommand;
import top.pdev.you.domain.command.activity.AddActivityCommand;
import top.pdev.you.domain.command.activity.GetActivityCommand;
import top.pdev.you.domain.command.activity.RuleCommand;
import top.pdev.you.domain.command.activity.UpdateActivityCommand;
import top.pdev.you.domain.entity.Activity;
import top.pdev.you.domain.entity.ActivityParticipant;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.model.dto.ActivityInfoDTO;
import top.pdev.you.domain.service.activity.ActivityService;
import top.pdev.you.domain.service.association.AssociationService;
import top.pdev.you.domain.service.student.StudentService;
import top.pdev.you.domain.service.user.UserService;
import top.pdev.you.domain.service.verification.VerificationService;
import top.pdev.you.infrastructure.mapper.ActivityInfoMapper;
import top.pdev.you.persistence.repository.ActivityParticipantRepository;
import top.pdev.you.persistence.repository.ActivityRepository;
import top.pdev.you.persistence.repository.ActivityRuleRepository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 活动服务实现
 * Created in 2023/4/24 20:47
 *
 * @author Peter1303
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private AssociationService associationService;

    @Resource
    private UserService userService;

    @Resource
    private StudentService studentService;

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private VerificationService verificationService;

    @Resource
    private ActivityRuleRepository activityRuleRepository;

    @Resource
    private ActivityParticipantRepository activityParticipantRepository;

    @Resource
    private ActivityInfoMapper activityInfoMapper;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public ActivityInfoDTO get(IdCommand command) {
        Long id = command.getId();
        Activity activity = activityRepository.getById(id);
        Optional.ofNullable(activity).orElseThrow(() -> new BusinessException("找不到活动"));
        return activityInfoMapper.convert(activity);
    }

    @Override
    public List<ActivityInfoDTO> list(GetActivityCommand command) {
        String search = command.getSearch();
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(search)) {
            queryWrapper.like(Activity::getTitle, search)
                    .or()
                    .like(Activity::getSummary, search)
                    .or()
                    .like(Activity::getDetail, search)
                    .or()
                    .like(Activity::getLocation, search);
        }
        List<Activity> activities = activityRepository.list(queryWrapper);
        return activities.stream()
                .sorted((a1, a2) -> {
                    LocalDateTime a1Time = a1.getTime();
                    LocalDateTime a2Time = a2.getTime();
                    return a2Time.compareTo(a1Time);
                })
                .map(activity -> activityInfoMapper.convert(activity))
                .collect(Collectors.toList());
    }

    @AccessPermission(permission = Permission.MANAGER)
    @Transactional
    @Override
    public void add(AddActivityCommand command) {
        String title = command.getTitle();
        String summary = command.getSummary();
        String detail = command.getDetail();
        String location = command.getLocation();
        Integer target = command.getTarget();
        Integer total = command.getTotal();
        TimeCommand time = command.getTime();
        // 生成活动
        User user = verificationService.currentUser();
        RoleEntity roleDomain = userService.getRoleDomain(user);
        Long associationId;
        if (roleDomain instanceof Manager) {
            Manager manager = (Manager) roleDomain;
            Association association = associationService.belongAssociation(manager);
            associationId = association.getId();
        } else {
            throw new BusinessException("必须负责人才可以发起活动");
        }
        Activity activity = new Activity();
        activity.setAssociationId(associationId);
        activity.setTitle(title);
        activity.setDetail(detail);
        activity.setSummary(summary);
        activity.setLocation(location);
        activity.setTime(LocalDateTime.now());
        activityRepository.save(activity);
        // 添加规则
        RuleCommand ruleCommand = new RuleCommand();
        ruleCommand.setTarget(target);
        ruleCommand.setTimeCommand(time);
        ruleCommand.setTotal(total);

        List<Activity.Rule> rules = activity.rules(ruleCommand);
        rules.forEach(rule -> activityRuleRepository.save(rule));

        // 推送事件
        ActivityEvent event = new ActivityEvent(activity);
        event.setActivity(activity);
        event.setRules(rules);
        applicationEventPublisher.publishEvent(event);
    }

    @AccessPermission(permission = Permission.MANAGER)
    @Transactional
    @Override
    public void update(UpdateActivityCommand command) {
        Long id = command.getId();
        Activity activity = activityRepository.getById(id);
        Optional.ofNullable(activity).orElseThrow(() -> new BusinessException("找不到活动"));
        String title = command.getTitle();
        String summary = command.getSummary();
        String detail = command.getDetail();
        String location = command.getLocation();
        Integer target = command.getTarget();
        Integer total = command.getTotal();
        TimeCommand time = command.getTime();
        activity.setTitle(title);
        activity.setSummary(summary);
        activity.setDetail(detail);
        activity.setLocation(location);
        activityRepository.saveOrUpdate(activity);
        RuleCommand ruleCommand = new RuleCommand();
        ruleCommand.setTarget(target);
        ruleCommand.setTimeCommand(time);
        ruleCommand.setTotal(total);
        List<Activity.Rule> rules = activity.rules(ruleCommand);
        Map<Integer, String> rulesMap = rules.stream()
                .collect(Collectors.toMap(Activity.Rule::getRule, Activity.Rule::getValue));
        List<Activity.Rule> activityRules = activityRuleRepository.findByActivity(activity);
        activityRules.forEach(rule -> {
            String value = rulesMap.get(rule.getRule());
            rule.setValue(value);
            activityRuleRepository.saveOrUpdate(rule);
        });
        // TODO 同步更新新的活动信息到知识库
    }

    @AccessPermission(permission = Permission.MANAGER)
    @Transactional
    @Override
    public void delete(IdCommand command) {
        Long id = command.getId();
        Activity activity = activityRepository.getById(id);
        Optional.ofNullable(activity).orElseThrow(() -> new BusinessException("找不到活动"));
        activityRepository.removeById(id);
        activityRuleRepository.deleteByActivity(activity);
        // TODO 同步删除知识库
    }

    @AccessPermission(permission = Permission.ADMIN, lower = true)
    @Transactional
    @Override
    public void participate(IdCommand command) {
        Long id = command.getId();
        Activity activity = activityRepository.getById(id);
        Optional.ofNullable(activity).orElseThrow(() -> new BusinessException("找不到活动"));
        User user = verificationService.currentUser();
        // 如果当前用户是社团管理员
        RoleEntity roleDomain = userService.getRoleDomain(user);
        if (roleDomain instanceof Manager) {
            Manager manager = (Manager) roleDomain;
            Association association = associationService.belongAssociation(manager);
            Long associationId = association.getId();
            // 如果活动就是该管理员的不允许参加
            if (associationId.equals(activity.getAssociationId())) {
                throw new BusinessException("社团负责人无法参加所发起的活动");
            }
        }
        // 检查是否已经参与了
        boolean joined = activityParticipantRepository.existsByActivityAndUser(activity, user);
        if (joined) {
            throw new BusinessException("你已参加");
        }
        List<Activity.Rule> rules = activityRuleRepository.findByActivity(activity);
        for (Activity.Rule rule : rules) {
            Integer tag = rule.getRule();
            // 根据活动规则确定对象
            if (tag == ActivityRule.TEACHER) {
                rule.validate(String.valueOf((roleDomain instanceof Teacher) ? 1 : 0));
            }
            // 根据规则确定年级
            if (tag == ActivityRule.GRADE) {
                if (roleDomain instanceof Student) {
                    Student student = (Student) roleDomain;
                    Integer grade = studentService.getGrade(student);
                    if (grade != 0) {
                        rule.validate(grade);
                    }
                }
            }
            // 没有参与的检查时间是否有效
            if (tag == ActivityRule.END_TIME) {
                rule.validate(DateUtil.now());
            }
            // 时间有效那么检查总人数
            if (tag == ActivityRule.TOTAL) {
                int total = (int) activityParticipantRepository.countByActivity(activity);
                rule.validate(total);
                // 人数有效顺便报名
                ActivityParticipant participant = new ActivityParticipant();
                participant.setUserId(user.getId());
                participant.setActivityId(activity.getId());
                participant.setTime(LocalDateTime.now());
                activityParticipantRepository.save(participant);
            }
        }
    }
}
