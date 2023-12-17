package top.pdev.you.domain.service.association;

import top.pdev.you.domain.entity.AssociationAudit;

/**
 * 社团审计服务
 * Created in 2023/12/16 10:50
 *
 * @author Peter1303
 */
public interface AssociationAuditService {
    /**
     * 更改状态
     *
     * @param audit  审计
     * @param status 状态
     */
    void changeStatus(AssociationAudit audit, boolean status);
}
