package top.pdev.you.application.event.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.pdev.you.application.event.ActivityEvent;
import top.pdev.you.common.constant.ActivityRule;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.command.ai.AddKnowledgeCommand;
import top.pdev.you.domain.entity.Activity;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.model.dto.ActivityInfoDTO;
import top.pdev.you.domain.model.dto.TimeRangeDTO;
import top.pdev.you.domain.service.ai.AiService;
import top.pdev.you.infrastructure.config.bean.AiConfig;
import top.pdev.you.infrastructure.mapper.ActivityInfoMapper;
import top.pdev.you.persistence.repository.AssociationRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 活动事件监听器
 * Created in 2023/12/17 14:12
 *
 * @author Peter1303
 */
@Slf4j
@Component
public class ActivityEventListener {
    @Resource
    private AiService aiService;

    @Resource
    private AssociationRepository associationRepository;

    @Resource
    private AiConfig aiConfig;

    @Resource
    private ActivityInfoMapper activityInfoMapper;

    @Async
    @EventListener(ActivityEvent.class)
    public void onEvent(ActivityEvent event) {
        Activity activity = event.getActivity();
        List<Activity.Rule> rules = event.getRules();
        Long associationId = activity.getAssociationId();
        Association association = associationRepository.findById(associationId);
        // 构建知识库数据
        AddKnowledgeCommand command = new AddKnowledgeCommand();
        command.setSource(association.getName());
        command.setPoint(activity.getTitle() + activity.getSummary());
        ActivityInfoDTO activityInfoDTO = activityInfoMapper.convert(activity, rules);
        TimeRangeDTO time = activityInfoDTO.getTime();
        // 年级
        Optional<Activity.Rule> gradeOne = rules.stream()
                .filter(r -> r.getRule() == ActivityRule.GRADE)
                .findFirst();
        // 人数
        Optional<Activity.Rule> totalOne = rules.stream()
                .filter(r -> r.getRule() == ActivityRule.TOTAL)
                .findFirst();
        gradeOne.orElseThrow(() -> new InternalErrorException("找不到活动对象规则"));
        totalOne.orElseThrow(() -> new InternalErrorException("找不到活动人数"));
        Integer grade = gradeOne.map(r -> Integer.valueOf(r.getValue())).get();
        Integer total = totalOne.map(r -> Integer.valueOf(r.getValue())).get();
        String content = StrUtil.format("开始{}|结束{}|地点{}|年级{}|人数{}|内容：{}",
                DateUtil.format(time.getStart(), "yyyy-MM-dd HH:mm"),
                DateUtil.format(time.getEnd(), "yyyy-MM-dd HH:mm"),
                activity.getLocation(),
                grade,
                total,
                activity.getDetail());
        command.setContent(content);
        command.setKnowledgeBookId(aiConfig.getKnowledgeId());
        aiService.add(command);
        log.info("推送知识库数据 {}", command);
    }
}
