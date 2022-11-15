package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.AssociationManagerDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.mapper.AssociationManagerMapper;
import top.pdev.you.domain.repository.AssociationManagerRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

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
        extends BaseRepository<AssociationManagerMapper, AssociationManagerDO>
        implements AssociationManagerRepository {
    @Resource
    private AssociationManagerMapper mapper;

    @Override
    public AssociationManagerDO getOne(Student student) {
        LambdaQueryWrapper<AssociationManagerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManagerDO::getUid, student.getUser().getUserId().getId());
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public List<AssociationManagerDO> getManagedList(Teacher teacher) {
        LambdaQueryWrapper<AssociationManagerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManagerDO::getUid, teacher.getUser().getUserId().getId());
        return mapper.selectList(queryWrapper);
    }

    @Override
    public boolean adminExists(AssociationId associationId, User user) {
        check(user);
        LambdaQueryWrapper<AssociationManagerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssociationManagerDO::getUid, user.getUserId().getId())
                .eq(AssociationManagerDO::getType, user.getPermission());
        return mapper.exists(queryWrapper);
    }
}
