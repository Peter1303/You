package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.AssociationAudit;
import top.pdev.you.domain.mapper.AssociationAuditMapper;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.base.BaseRepository;
import top.pdev.you.interfaces.model.dto.AssociationAuditDTO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 社团审核仓库实现类
 * Created in 2022/11/9 13:12
 *
 * @author Peter1303
 */
@Repository
public class AssociationAuditRepositoryImpl
        extends BaseRepository<AssociationAuditMapper, AssociationAudit>
        implements AssociationAuditRepository {
    @Resource
    private AssociationAuditMapper mapper;

    @Override
    public AssociationAudit findById(Long id) {
        LambdaQueryWrapper<AssociationAudit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationAudit::getId, id);
        AssociationAudit auditDO = mapper.selectOne(queryWrapper);
        Optional.ofNullable(auditDO)
                .orElseThrow(() -> new BusinessException("找不到审核项目"));
        return auditDO;
    }

    @Override
    public AssociationAudit findByStudentIdAndAssociationIdAndStatusNull(Long studentId, Long associationId) {
        LambdaQueryWrapper<AssociationAudit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationAudit::getStudentId, studentId)
                .eq(AssociationAudit::getAssociationId, associationId)
                .isNull(AssociationAudit::getStatus);
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public List<AssociationAuditDTO> getAuditList() {
        return mapper.getAuditList();
    }
}
