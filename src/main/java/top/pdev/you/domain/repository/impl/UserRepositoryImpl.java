package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.mapper.UserMapper;
import top.pdev.you.domain.repository.UserRepository;

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
        extends ServiceImpl<UserMapper, UserDO>
        implements UserRepository {
    @Resource
    private UserMapper mapper;

    @Override
    public User find(UserId userId) {
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
    public User findByOpenId(String openId) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getWechatId, openId);
        UserDO userDO = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(userDO).isPresent()) {
            return null;
        }
        // TODO cache
        return new User(userDO);
    }
}
