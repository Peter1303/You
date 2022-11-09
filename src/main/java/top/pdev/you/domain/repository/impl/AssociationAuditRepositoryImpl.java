package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.mapper.AssociationAuditMapper;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;

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
}
