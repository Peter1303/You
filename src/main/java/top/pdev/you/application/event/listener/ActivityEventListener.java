package top.pdev.you.application.event.listener;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.pdev.you.application.event.ActivityEvent;
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
        String content = StrUtil.format("开始：{}|结束：{}|内容：{}",
                time.getStart(),
                time.getEnd(),
                activity.getDetail());
        command.setContent(content);
        command.setKnowledgeBookId(aiConfig.getKnowledgeId());
        aiService.add(command);
        log.info("推送知识库数据 {}", command);
    }
}
