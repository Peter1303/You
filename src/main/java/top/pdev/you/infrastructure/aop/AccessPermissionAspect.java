package top.pdev.you.infrastructure.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.PermissionDeniedException;
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

    @Before("execution(* top.pdev.you.interfaces.facade.*.*(..)) " +
            "&& @annotation(accessPermission) ")
    public void checkPermission(JoinPoint point, AccessPermission accessPermission) {
        Permission permission = accessPermission.permission();
        HttpServletRequest request = RequestUtil.getRequest();
        String token = TokenUtil.getTokenByHeader(request);
        boolean manager = adminService.isManager(token);
        boolean admin = adminService.isAdmin(token);
        boolean superAdmin = adminService.isSuperAdmin(token);
        switch (permission) {
            case MANAGER:
                if (!manager) {
                    throw new PermissionDeniedException();
                }
                break;
            case ADMIN:
                if (!admin) {
                    throw new PermissionDeniedException();
                }
                break;
            case SUPER:
                if (!superAdmin) {
                    throw new PermissionDeniedException();
                }
                break;
            default:
                break;
        }
    }
}
