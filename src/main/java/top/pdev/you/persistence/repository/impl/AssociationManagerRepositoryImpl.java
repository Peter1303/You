package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.persistence.mapper.AssociationManagerMapper;
import top.pdev.you.persistence.repository.AssociationManagerRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.List;

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
    public List<AssociationManager> findByUserIdAndType(Long id, Integer type) {
        LambdaQueryWrapper<AssociationManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManager::getUserId, id)
                .eq(AssociationManager::getType, type);
        List<AssociationManager> list = mapper.selectList(queryWrapper);
        if (list.isEmpty()) {
            throw new BusinessException("找不到用户所管理的社团");
        }
        return list;
    }

    @Override
    public List<AssociationManager> getManagedList(Teacher teacher) {
        LambdaQueryWrapper<AssociationManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManager::getUserId, teacher.getUserId());
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
