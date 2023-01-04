package top.pdev.you.infrastructure.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.PermissionDeniedException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.AdminService;
import top.pdev.you.infrastructure.util.RequestUtil;
import top.pdev.you.infrastructure.util.TokenUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    private AdminService adminService;

    @Resource
    private UserRepository userRepository;

    @Before("execution(* top.pdev.you.interfaces.facade.*.*(..)) " +
            "&& @annotation(accessPermission) ")
    public void checkPermission(JoinPoint point, AccessPermission accessPermission) {
        Permission permission = accessPermission.permission();
        boolean lower = accessPermission.lower();
        // 指定权限
        boolean specified = accessPermission.specified();
        HttpServletRequest request = RequestUtil.getRequest();
        String token = TokenUtil.getTokenByHeader(request);
        User user = userRepository.findByWechatId(token);
        Integer currPermission = user.getPermission();
        boolean manager = adminService.isManager(token);
        boolean admin = adminService.isAdmin(token);
        boolean superAdmin = adminService.isSuperAdmin(token);
        if (specified) {
            if (permission.getValue() != currPermission) {
                throw new PermissionDeniedException("功能不可用");
            }
        }
        if (lower) {
            if (permission.getValue() < currPermission) {
                throw new PermissionDeniedException("功能不可用");
            }
        } else {
            boolean ok = true;
            switch (permission) {
                case SUPER:
                    ok = superAdmin;
                    break;
                case ADMIN:
                    ok = admin;
                    break;
                case MANAGER:
                    ok = manager;
                    break;
            }
            if (!ok) {
                throw new PermissionDeniedException();
            }
        }
    }
}
