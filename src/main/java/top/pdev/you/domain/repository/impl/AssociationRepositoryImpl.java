package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.AssociationManagerDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.mapper.AssociationMapper;
import top.pdev.you.domain.repository.AssociationManagerRepository;
import top.pdev.you.domain.repository.AssociationRepository;

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
        extends ServiceImpl<AssociationMapper, AssociationDO>
        implements AssociationRepository {
    @Resource
    private AssociationMapper mapper;

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
}
