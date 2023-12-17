package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.AssociationParticipant;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.persistence.mapper.AssociationParticipateMapper;
import top.pdev.you.persistence.repository.AssociationParticipateRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 社团成员仓库实现类
 * Created in 2022/11/9 15:40
 *
 * @author Peter1303
 */
@Repository
public class AssociationParticipateRepositoryImpl
        extends BaseRepository<AssociationParticipateMapper, AssociationParticipant>
        implements AssociationParticipateRepository {
    @Resource
    private AssociationParticipateMapper mapper;

    @Override
    public List<AssociationParticipant> getParticipateList(Student student) {
        LambdaQueryWrapper<AssociationParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationParticipant::getStudentId, student.getId());
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<AssociationParticipant> findByAssociationId(Long associationId) {
        LambdaQueryWrapper<AssociationParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationParticipant::getAssociationId, associationId);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public long countByAssociationId(long id) {
        LambdaQueryWrapper<AssociationParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationParticipant::getAssociationId, id);
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public boolean deleteByAssociationIdAndStudentId(Long associationId, Long studentId) {
        LambdaQueryWrapper<AssociationParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationParticipant::getAssociationId, associationId)
                .eq(AssociationParticipant::getStudentId, studentId);
        return remove(queryWrapper);
    }

    @Override
    public boolean existsByStudentIdAndAssociationId(Long studentId, Long associationId) {
        LambdaQueryWrapper<AssociationParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationParticipant::getStudentId, studentId)
                .eq(AssociationParticipant::getAssociationId, associationId);
        return mapper.exists(queryWrapper);
    }
}
