package top.pdev.you.application.event;

import org.springframework.context.ApplicationEvent;

/**
 * 审核事件
 * Created in 2022/11/10 17:01
 *
 * @author Peter1303
 */
public class AuditEvent extends ApplicationEvent {
    public AuditEvent(Object source) {
        super(source);
    }
}
