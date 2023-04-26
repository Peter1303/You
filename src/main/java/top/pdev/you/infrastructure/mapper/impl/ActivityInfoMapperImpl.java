package top.pdev.you.infrastructure.mapper.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;
import top.pdev.you.application.service.verification.VerificationService;
import top.pdev.you.common.constant.ActivityRule;
import top.pdev.you.common.constant.ActivityTarget;
import top.pdev.you.domain.entity.Activity;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.ui.dto.ActivityInfoDTO;
import top.pdev.you.domain.ui.dto.AssociationBaseInfoDTO;
import top.pdev.you.domain.ui.dto.TimeRangeDTO;
import top.pdev.you.infrastructure.mapper.ActivityInfoMapper;
import top.pdev.you.infrastructure.mapper.AssociationMapper;
import top.pdev.you.persistence.repository.ActivityParticipantRepository;
import top.pdev.you.persistence.repository.ActivityRuleRepository;
import top.pdev.you.persistence.repository.AssociationRepository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 活动映射
 * Created in 2023/4/25 20:34
 *
 * @author Peter1303
 */
@Component
public class ActivityInfoMapperImpl implements ActivityInfoMapper {
    @Resource
    private VerificationService verificationService;

    @Resource
    private ActivityRuleRepository activityRuleRepository;

    @Resource
    private AssociationRepository associationRepository;

    @Resource
    private ActivityParticipantRepository activityParticipantRepository;

    @Override
    public ActivityInfoDTO convert(Activity activity) {
        List<Activity.Rule> rules = activityRuleRepository.findByActivity(activity);
        Long associationId = activity.getAssociationId();
        Association association = associationRepository.findById(associationId);
        AssociationBaseInfoDTO associationBaseInfoDTO = AssociationMapper.INSTANCE.convert(association);
        ActivityInfoDTO dto = new ActivityInfoDTO();
        TimeRangeDTO timeRangeDTO = new TimeRangeDTO();
        Map<Integer, String> rulesMap = rules.stream()
                .collect(Collectors.toMap(Activity.Rule::getRule, Activity.Rule::getValue));
        LocalDateTime start = DateUtil.parse(rulesMap.get(ActivityRule.START_TIME)).toLocalDateTime();
        LocalDateTime end = DateUtil.parse(rulesMap.get(ActivityRule.END_TIME)).toLocalDateTime();
        timeRangeDTO.setStart(start);
        timeRangeDTO.setEnd(end);
        User user = verificationService.currentUser();
        int grade = Integer.parseInt(rulesMap.get(ActivityRule.GRADE));
        boolean teacher = rulesMap.get(ActivityRule.TEACHER).equals("1");
        int target = grade + (teacher ? ActivityTarget.TEACHER_FLAG : 0);
        long current = activityParticipantRepository.countByActivity(activity);
        boolean status = activityParticipantRepository.existsByActivityAndUser(activity, user);
        dto.setId(activity.getId());
        dto.setTitle(activity.getTitle());
        dto.setSummary(activity.getSummary());
        dto.setDetail(activity.getDetail());
        dto.setLocation(activity.getLocation());
        dto.setCreatedTime(activity.getTime());
        dto.setAssociation(associationBaseInfoDTO);
        dto.setTime(timeRangeDTO);
        dto.setCurrent((int) current);
        dto.setTotal(Integer.valueOf(rulesMap.get(ActivityRule.TOTAL)));
        dto.setTarget(target);
        dto.setStatus(status);
        return dto;
    }
}
