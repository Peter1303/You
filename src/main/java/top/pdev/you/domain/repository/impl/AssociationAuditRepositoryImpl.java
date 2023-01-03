package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
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
        extends BaseRepository<AssociationAuditMapper, AssociationAuditDO>
        implements AssociationAuditRepository {
    @Resource
    private AssociationAuditMapper mapper;

    @Override
    public AssociationAuditDO findById(Long id) {
        LambdaQueryWrapper<AssociationAuditDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationAuditDO::getId, id);
        AssociationAuditDO auditDO = mapper.selectOne(queryWrapper);
        Optional.ofNullable(auditDO)
                .orElseThrow(() -> new BusinessException("找不到审核项目"));
        return auditDO;
    }

    @Override
    public AssociationAuditDO findByStudentIdAndAssociationId(Long studentId, Long associationId) {
        LambdaQueryWrapper<AssociationAuditDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationAuditDO::getStudentId, studentId)
                .eq(AssociationAuditDO::getAssociationId, associationId);
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public List<AssociationAuditDTO> getAuditList() {
        return mapper.getAuditList();
    }

    @Override
    public boolean existsByStudentIdAndAssociationId(Long studentId, Long id) {
        LambdaQueryWrapper<AssociationAuditDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationAuditDO::getStudentId, studentId)
                .eq(AssociationAuditDO::getAssociationId, id);
        return mapper.exists(queryWrapper);
    }
}
