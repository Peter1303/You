package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.entity.User;
import top.pdev.you.persistence.mapper.UserMapper;
import top.pdev.you.persistence.repository.UserRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

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
        extends BaseRepository<UserMapper, User>
        implements UserRepository {
    @Resource
    private UserMapper mapper;

    @Override
    public User findById(Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId);
        User user = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(user).isPresent()) {
            return null;
        }
        // TODO cache
        return user;
    }

    @Override
    public User findByWechatId(String wechatId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getWechatId, wechatId);
        User user = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(user).isPresent()) {
            return null;
        }
        // TODO cache
        return user;
    }

    @Override
    public Boolean superAdminExists() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPermission, Permission.SUPER.getValue());
        return mapper.exists(queryWrapper);
    }

    @Override
    public boolean deleteById(Long id) {
        return removeById(id);
    }
}
