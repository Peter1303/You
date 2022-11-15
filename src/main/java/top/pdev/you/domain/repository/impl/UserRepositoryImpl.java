package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.mapper.UserMapper;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户仓库实现类
 * Created in 2022/10/2 13:15
 *
 * @author Peter1303
 */
@Repository
public class UserRepositoryImpl
        extends BaseRepository<UserMapper, UserDO>
        implements UserRepository {
    @Resource
    private UserMapper mapper;

    @Override
    public User find(UserId userId) {
        checkId(userId);
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getId, userId.getId());
        UserDO userDO = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(userDO).isPresent()) {
            return null;
        }
        // TODO cache
        return new User(userDO);
    }

    @Override
    public User find(StudentId studentId) {
        checkId(studentId);
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getTargetId, studentId.getId())
                .and(wrapper -> wrapper.between(UserDO::getPermission,
                        Permission.USER.getValue(),
                        Permission.MANAGER.getValue()));
        UserDO userDO = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(userDO).isPresent()) {
            return null;
        }
        // TODO cache
        return new User(userDO);
    }

    @Override
    public User findByToken(String openId) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getWechatId, openId);
        UserDO userDO = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(userDO).isPresent()) {
            return null;
        }
        // TODO cache
        return new User(userDO);
    }

    @Override
    public Boolean superAdminExists() {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getPermission, Permission.SUPER.getValue());
        return mapper.exists(queryWrapper);
    }

    @Override
    public boolean delete(UserId id) {
        checkId(id);
        return removeById(id.getId());
    }
}
