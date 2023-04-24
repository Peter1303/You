package top.pdev.you.application.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.PermissionDeniedException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.persistence.repository.UserRepository;
import top.pdev.you.application.service.permission.PermissionService;
import top.pdev.you.infrastructure.util.RequestUtil;
import top.pdev.you.infrastructure.util.TokenUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 访问权限切面
 * Created in 2022/8/19 22:29
 *
 * @author Peter1303
 */
@Slf4j
@Aspect
@Component
public class AccessPermissionAspect {
    @Resource
    private PermissionService permissionService;

    @Resource
    private UserRepository userRepository;

    @Before("execution(* top.pdev.you.web..*.*(..)) " +
            "&& @annotation(accessPermission) ")
    public void checkPermission(JoinPoint point, AccessPermission accessPermission) {
        Permission[] permissions = accessPermission.permission();
        boolean lower = accessPermission.lower();
        // 指定权限
        boolean specified = accessPermission.specified();
        HttpServletRequest request = RequestUtil.getRequest();
        String token = TokenUtil.getTokenByHeader(request);
        User user = userRepository.findByWechatId(token);
        Integer currPermission = user.getPermission();
        boolean manager = permissionService.isManager(token);
        boolean admin = permissionService.isAdmin(token);
        boolean superAdmin = permissionService.isSuperAdmin(token);
        if (specified) {
            boolean ok = Arrays.stream(permissions)
                    .anyMatch(permission ->
                            permission.getValue() == currPermission);
            if (!ok) {
                throw new PermissionDeniedException("功能不可用");
            }
        }
        if (lower) {
            boolean bigger = Arrays.stream(permissions)
                    .anyMatch(permission ->
                            permission.getValue() < currPermission);
            if (bigger) {
                throw new PermissionDeniedException("功能不可用");
            }
        } else {
            boolean ok = Arrays.stream(permissions)
                    .anyMatch(permission -> {
                        switch (permission) {
                            case SUPER:
                                return superAdmin;
                            case ADMIN:
                                return admin;
                            case MANAGER:
                                return manager;
                        }
                        return false;
                    });
            if (!ok) {
                throw new PermissionDeniedException();
            }
        }
    }
}
