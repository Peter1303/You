package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.enums.RedisKey;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.entity.role.SuperEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.PermissionService;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.result.ResultCode;
import top.pdev.you.infrastructure.util.TagKeyUtil;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 管理员服务
 * Created in 2022/8/19 21:53
 *
 * @author Peter1303
 */
@Service
public class PermissionServiceImpl implements PermissionService {
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
        Integer permission = redisService.getObject(TagKeyUtil.get(RedisKey.PERMISSION, uidOrToken), Integer.class);
        if (Optional.ofNullable(permission).isPresent()) {
            return permission >= Permission.MANAGER.getValue();
        }
        return false;
    }

    @Override
    public boolean isAdmin(Object uidOrToken) {
        check(uidOrToken);
        Integer permission = redisService.getObject(TagKeyUtil.get(RedisKey.PERMISSION, uidOrToken), Integer.class);
        if (Optional.ofNullable(permission).isPresent()) {
            return permission >= Permission.ADMIN.getValue();
        }
        return false;
    }

    @Override
    public boolean isSuperAdmin(Object uidOrToken) {
        check(uidOrToken);
        Integer permission = redisService.getObject(TagKeyUtil.get(RedisKey.PERMISSION, uidOrToken), Integer.class);
        if (Optional.ofNullable(permission).isPresent()) {
            return permission >= Permission.SUPER.getValue();
        }
        return false;
    }

    @Override
    public boolean isSelf(User user, Long userId) {
        return Objects.equals(user.getId(), userId);
    }

    @Override
    public boolean editable(User currentUser, Long targetUserId) {
        if (isSelf(currentUser, targetUserId)) {
            return true;
        }
        RoleEntity role = currentUser.getRoleDomain();
        User targetUser = userRepository.findById(targetUserId);
        if (targetUser.getPermission() > currentUser.getPermission()) {
            return false;
        }
        if (role instanceof Manager) {
            return true;
        }
        if (role instanceof Teacher) {
            return true;
        }
        return role instanceof SuperEntity;
    }

    private void check(Object uidOrToken) {
        User user;
        Optional.ofNullable(uidOrToken).orElseThrow(() -> new BusinessException(ResultCode.PERMISSION_DENIED));
        if (uidOrToken instanceof Long) {
            user = userRepository.findById((Long) uidOrToken);
        } else if (uidOrToken instanceof String) {
            String token = (String) uidOrToken;
            user = userRepository.findByWechatId(token);
        } else {
            throw new InternalErrorException("传值错误");
        }
        if (Optional.ofNullable(user).isPresent()) {
            redisService.set(TagKeyUtil.get(RedisKey.PERMISSION, uidOrToken), user.getPermission(),
                    1, TimeUnit.DAYS);
        }
    }
}
