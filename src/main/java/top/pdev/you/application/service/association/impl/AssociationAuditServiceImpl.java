package top.pdev.you.application.service.association.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.application.service.association.AssociationAuditService;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.AssociationAudit;
import top.pdev.you.persistence.repository.AssociationAuditRepository;

import javax.annotation.Resource;

/**
 * 社团审计服务实现类
 * Created in 2023/12/16 10:50
 *
 * @author Peter1303
 */
@Service
public class AssociationAuditServiceImpl implements AssociationAuditService {
    @Resource
    private AssociationAuditRepository associationAuditRepository;

    @Override
    public void changeStatus(AssociationAudit audit, boolean status) {
        audit.setId(audit.getId());
        audit.setStatus(status);
        boolean update = associationAuditRepository.updateById(audit);
        if (!update) {
            throw new BusinessException("无法更改入团审核状态");
        }
    }
}
