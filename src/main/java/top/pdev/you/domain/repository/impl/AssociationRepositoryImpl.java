package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.AssociationManagerDO;
import top.pdev.you.domain.entity.data.AssociationParticipantDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.mapper.AssociationMapper;
import top.pdev.you.domain.mapper.AssociationParticipateMapper;
import top.pdev.you.domain.repository.AssociationManagerRepository;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 社团管理仓库实现类
 * Created in 2022/10/23 21:38
 *
 * @author Peter1303
 */
@Repository
public class AssociationRepositoryImpl
        extends BaseRepository<AssociationMapper, AssociationDO>
        implements AssociationRepository {
    @Resource
    private AssociationMapper mapper;

    @Resource
    private AssociationParticipateMapper associationParticipateMapper;

    @Resource
    private AssociationFactory associationFactory;

    @Resource
    private AssociationManagerRepository associationManagerRepository;

    @Override
    public Association getOne(Student student) {
        AssociationManagerDO managerDO = associationManagerRepository.getOne(student);
        Optional.ofNullable(managerDO).orElseThrow(() -> new InternalErrorException("找不到管理信息"));
        LambdaQueryWrapper<AssociationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationDO::getId, managerDO.getId());
        AssociationDO associationDO = mapper.selectOne(queryWrapper);
        return associationFactory.getAssociation(associationDO);
    }

    @Override
    public Association getOne(Id id) {
        Optional.ofNullable(id).orElseThrow(() -> new InternalErrorException("社团 ID 为空"));
        return associationFactory.getAssociation(getById(id.getId()));
    }

    @Override
    public List<AssociationDO> getManagedList(Teacher teacher) {
        LambdaQueryWrapper<AssociationDO> queryWrapper = new LambdaQueryWrapper<>();
        List<AssociationManagerDO> managedList = associationManagerRepository.getManagedList(teacher);
        if (managedList.isEmpty()) {
            return new ArrayList<>();
        }
        queryWrapper.in(AssociationDO::getId,
                managedList
                        .stream()
                        .mapToLong(AssociationManagerDO::getId));
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<AssociationDO> ofStudentList(Student student) {
        LambdaQueryWrapper<AssociationParticipantDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationParticipantDO::getUid, student.getUser().getUserId().getId());
        List<AssociationParticipantDO> participantDOs =
                associationParticipateMapper.selectList(queryWrapper);
        if (participantDOs.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<AssociationDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(AssociationDO::getId,
                participantDOs
                        .stream()
                        .mapToLong(AssociationParticipantDO::getAssociationId));
        return mapper.selectList(lambdaQueryWrapper);
    }
}
