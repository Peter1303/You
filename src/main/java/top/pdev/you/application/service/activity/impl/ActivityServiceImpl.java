package top.pdev.you.application.service.activity.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.activity.ActivityService;
import top.pdev.you.application.service.verification.VerificationService;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Activity;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.ui.dto.ActivityInfoDTO;
import top.pdev.you.infrastructure.mapper.ActivityInfoMapper;
import top.pdev.you.persistence.repository.ActivityRepository;
import top.pdev.you.persistence.repository.ActivityRuleRepository;
import top.pdev.you.web.activity.command.AddActivityCommand;
import top.pdev.you.web.activity.command.RuleCommand;
import top.pdev.you.web.activity.command.UpdateActivityCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.command.TimeCommand;

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
    private ActivityRepository activityRepository;

    @Resource
    private VerificationService verificationService;

    @Resource
    private ActivityRuleRepository activityRuleRepository;

    @Resource
    private ActivityInfoMapper activityInfoMapper;

    @Override
    public ActivityInfoDTO get(IdCommand command) {
        Long id = command.getId();
        Activity activity = activityRepository.getById(id);
        Optional.ofNullable(activity).orElseThrow(() -> new BusinessException("找不到活动"));
        return activityInfoMapper.convert(activity);
    }

    @Override
    public List<ActivityInfoDTO> list() {
        List<Activity> activities = activityRepository.list();
        return activities.stream()
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
        RoleEntity roleDomain = user.getRoleDomain();
        Long associationId;
        if (roleDomain instanceof Manager) {
            Manager manager = (Manager) roleDomain;
            Association association = manager.belongAssociation();
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
    }
}
