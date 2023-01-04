package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.mapper.AssociationManagerMapper;
import top.pdev.you.domain.repository.AssociationManagerRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 社团管理仓库实现类
 * Created in 2022/10/24 17:01
 *
 * @author Peter1303
 */
@Repository
public class AssociationManagerRepositoryImpl
        extends BaseRepository<AssociationManagerMapper, AssociationManager>
        implements AssociationManagerRepository {
    @Resource
    private AssociationManagerMapper mapper;

    @Override
    public AssociationManager findByUserId(Long id) {
        LambdaQueryWrapper<AssociationManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManager::getUserId, id);
        AssociationManager one = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(one).isPresent()) {
            throw new BusinessException("找不到用户所管理的社团");
        }
        return one;
    }

    @Override
    public List<AssociationManager> getManagedList(Teacher teacher) {
        LambdaQueryWrapper<AssociationManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManager::getUserId, teacher.getUser().getId());
        return mapper.selectList(queryWrapper);
    }

    @Override
    public boolean existsByAssociationIdAndUserIdAndType(Long associationId,
                                                         Long userId,
                                                         Integer permission) {
        LambdaQueryWrapper<AssociationManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManager::getAssociationId, associationId)
                .eq(AssociationManager::getUserId, userId)
                .eq(AssociationManager::getType, permission);
        return mapper.exists(queryWrapper);
    }

    @Override
    public boolean deleteByAssociationAndUserId(Long associationId, Long userId) {
        LambdaQueryWrapper<AssociationManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManager::getAssociationId, associationId)
                .eq(AssociationManager::getUserId, userId);
        return remove(queryWrapper);
    }
}
