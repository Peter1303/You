package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.AssociationParticipant;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.persistence.mapper.AssociationMapper;
import top.pdev.you.persistence.repository.AssociationManagerRepository;
import top.pdev.you.persistence.repository.AssociationParticipateRepository;
import top.pdev.you.persistence.repository.AssociationRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 社团管理仓库实现类
 * Created in 2022/10/23 21:38
 *
 * @author Peter1303
 */
@Repository
public class AssociationRepositoryImpl
        extends BaseRepository<AssociationMapper, Association>
        implements AssociationRepository {
    @Resource
    private AssociationMapper mapper;

    @Resource
    private AssociationParticipateRepository associationParticipateRepository;

    @Resource
    private AssociationManagerRepository associationManagerRepository;

    @Override
    public Association findById(Long associationId) {
        Association association = getById(associationId);
        Optional.ofNullable(association)
                .orElseThrow(() -> new BusinessException("没有这个社团"));
        return association;
    }

    @Override
    public List<Association> getManagedList(Teacher teacher) {
        LambdaQueryWrapper<Association> queryWrapper = new LambdaQueryWrapper<>();
        List<AssociationManager> managedList = associationManagerRepository.getManagedList(teacher);
        if (managedList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> list = managedList
                .stream()
                .map(AssociationManager::getId)
                .collect(Collectors.toList());
        queryWrapper.in(Association::getId, list);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<Association> ofStudentList(Student student) {
        List<AssociationParticipant> participantDOs =
                associationParticipateRepository.getParticipateList(student);
        if (participantDOs.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<Association> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Long> list = participantDOs
                .stream()
                .map(AssociationParticipant::getAssociationId)
                .collect(Collectors.toList());
        lambdaQueryWrapper.in(Association::getId, list);
        return mapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public boolean existsByName(String name) {
        LambdaQueryWrapper<Association> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Association::getName, name);
        return mapper.exists(queryWrapper);
    }

    @Override
    public boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public List<Association> findNameContaining(String name) {
        LambdaQueryWrapper<Association> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Association::getName, name);
        return mapper.selectList(queryWrapper);
    }
}
