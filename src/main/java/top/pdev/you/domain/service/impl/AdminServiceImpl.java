package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.constant.RedisKey;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.AdminService;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.result.ResultCode;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 管理员服务
 * Created in 2022/8/19 21:53
 *
 * @author Peter1303
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private RedisService redisService;

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean hasSuperAdmin() {
        return userRepository.superAdminExists();
    }

    @Override
    public boolean isManager(Object uidOrToken) {
        check(uidOrToken);
        Integer permission = redisService.getObject(RedisKey.permission(uidOrToken), Integer.class);
        if (Optional.ofNullable(permission).isPresent()) {
            return permission >= Permission.MANAGER.getValue();
        }
        return false;
    }

    @Override
    public boolean isAdmin(Object uidOrToken) {
        check(uidOrToken);
        Integer permission = redisService.getObject(RedisKey.permission(uidOrToken), Integer.class);
        if (Optional.ofNullable(permission).isPresent()) {
            return permission >= Permission.ADMIN.getValue();
        }
        return false;
    }

    @Override
    public boolean isSuperAdmin(Object uidOrToken) {
        check(uidOrToken);
        Integer permission = redisService.getObject(RedisKey.permission(uidOrToken), Integer.class);
        if (Optional.ofNullable(permission).isPresent()) {
            return permission >= Permission.SUPER.getValue();
        }
        return false;
    }

    private void check(Object uidOrToken) {
        User user;
        Optional.ofNullable(uidOrToken).orElseThrow(() -> new BusinessException(ResultCode.PERMISSION_DENIED));
        if (uidOrToken instanceof Long) {
            user = userRepository.findById((Long) uidOrToken);
        } else if (uidOrToken instanceof String) {
            String token = (String) uidOrToken;
            user = userRepository.findByOpenId(token);
        } else {
            throw new InternalErrorException("传值错误");
        }
        if (Optional.ofNullable(user).isPresent()) {
            redisService.set(RedisKey.permission(uidOrToken), user.getPermission(),
                    1, TimeUnit.DAYS);
        }
    }
}
