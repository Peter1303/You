package top.pdev.you.application.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.pdev.you.application.event.AssociationAuditEvent;
import top.pdev.you.application.event.AuditEvent;
import top.pdev.you.application.service.WechatMessageService;
import top.pdev.you.common.entity.wechat.template.AuditTemplate;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;

import javax.annotation.Resource;

/**
 * 审核事件监听者
 * Created in 2022/11/10 16:58
 *
 * @author Peter1303
 */
@Slf4j
@Component
public class AuditEventListener {
    @Resource
    private WechatMessageService wechatMessageService;

    @Async
    @EventListener(AuditEvent.class)
    public void onEvent(AuditEvent event) {
        // 社团审核事件
        if (event instanceof AssociationAuditEvent) {
            AssociationAuditEvent auditEvent = (AssociationAuditEvent) event;
            Association association = auditEvent.getAssociation();
            Student student = auditEvent.getStudent();
            String openId = student.getUser().getWechatId();
            AuditTemplate auditTemplate = new AuditTemplate();
            auditTemplate.setTitle("社团加入审核");
            auditTemplate.setName(association.getName());
            auditTemplate.setResult(auditEvent.isPassed() ? "通过" : "拒绝");
            boolean sent = wechatMessageService.sendTemplateMessage(
                    openId,
                    "",
                    auditTemplate
            );
            if (!sent) {
                log.warn("发送审核模板消息失败");
            }
        }
    }
}
