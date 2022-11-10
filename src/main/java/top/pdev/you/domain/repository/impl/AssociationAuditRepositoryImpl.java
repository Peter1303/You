package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.mapper.AssociationAuditMapper;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.base.BaseRepository;
import top.pdev.you.interfaces.model.dto.AssociationAuditDTO;

import javax.annotation.Resource;
import java.util.List;

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
    public AssociationAuditDO getOne(StudentId studentId, AssociationId associationId) {
        checkId(studentId);
        checkId(associationId);
        LambdaQueryWrapper<AssociationAuditDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationAuditDO::getStudentId, studentId.getId())
                .eq(AssociationAuditDO::getAssociationId, associationId.getId());
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public boolean exists(StudentId studentId, AssociationId id) {
        checkId(studentId);
        checkId(id);
        LambdaQueryWrapper<AssociationAuditDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationAuditDO::getStudentId, studentId.getId())
                .eq(AssociationAuditDO::getAssociationId, id.getId());
        return mapper.exists(queryWrapper);
    }

    @Override
    public List<AssociationAuditDTO> getAuditList() {
        return mapper.getAuditList();
    }
}
