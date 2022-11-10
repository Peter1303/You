package top.pdev.you.application.event;

import lombok.Getter;
import lombok.Setter;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;

/**
 * 社团审核事件
 * Created in 2022/11/10 16:57
 *
 * @author Peter1303
 */
public class AssociationAuditEvent extends AuditEvent {
    @Setter
    @Getter
    private Association association;

    @Setter
    @Getter
    private Student student;

    @Setter
    @Getter
    private boolean passed = false;

    public AssociationAuditEvent(Object source) {
        super(source);
    }
}
