package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.data.AssociationParticipantDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.mapper.AssociationParticipateMapper;
import top.pdev.you.domain.repository.AssociationParticipateRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

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
        extends BaseRepository<AssociationParticipateMapper, AssociationParticipantDO>
        implements AssociationParticipateRepository {
    @Resource
    private AssociationParticipateMapper mapper;

    @Override
    public List<AssociationParticipantDO> getParticipateList(Student student) {
        LambdaQueryWrapper<AssociationParticipantDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationParticipantDO::getStudentId, student.getStudentId().getId());
        return mapper.selectList(queryWrapper);
    }

    @Override
    public boolean exists(StudentId studentId, AssociationId associationId) {
        checkId(studentId);
        checkId(associationId);
        LambdaQueryWrapper<AssociationParticipantDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationParticipantDO::getStudentId, studentId.getId())
                .eq(AssociationParticipantDO::getAssociationId, associationId.getId());
        return mapper.exists(queryWrapper);
    }
}
