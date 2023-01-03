package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.AssociationParticipantDO;
import top.pdev.you.domain.mapper.AssociationMapper;
import top.pdev.you.domain.repository.AssociationManagerRepository;
import top.pdev.you.domain.repository.AssociationParticipateRepository;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.base.BaseRepository;
import top.pdev.you.interfaces.model.dto.AssociationInfoDTO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

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
        extends BaseRepository<AssociationMapper, AssociationDO>
        implements AssociationRepository {
    @Resource
    private AssociationMapper mapper;

    @Resource
    private AssociationParticipateRepository associationParticipateRepository;

    @Resource
    private AssociationManagerRepository associationManagerRepository;

    @Override
    public Association findById(Long associationId) {
        AssociationDO associationDO = getById(associationId);
        Optional.ofNullable(associationDO)
                .orElseThrow(() -> new BusinessException("没有这个社团"));
        return new Association(associationDO);
    }

    @Override
    public List<AssociationDO> getManagedList(Teacher teacher) {
        LambdaQueryWrapper<AssociationDO> queryWrapper = new LambdaQueryWrapper<>();
        List<AssociationManager> managedList = associationManagerRepository.getManagedList(teacher);
        if (managedList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> list = managedList
                .stream()
                .map(AssociationManager::getId)
                .collect(Collectors.toList());
        queryWrapper.in(AssociationDO::getId, list);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<AssociationDO> ofStudentList(Student student) {
        List<AssociationParticipantDO> participantDOs =
                associationParticipateRepository.getParticipateList(student);
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

    @Override
    public List<AssociationInfoDTO> getInfoList(SearchVO searchVO) {
        return mapper.getInfoList(searchVO);
    }

    @Override
    public boolean existsByName(String name) {
        LambdaQueryWrapper<AssociationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(AssociationDO::getName, name);
        return mapper.exists(queryWrapper);
    }
}
