package top.pdev.you.domain.service.user;

import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.enums.Role;
import top.pdev.you.domain.command.user.RegisterCommand;
import top.pdev.you.domain.command.user.SetProfileCommand;
import top.pdev.you.domain.command.user.UserLoginCommand;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.model.vm.LoginResultResponse;
import top.pdev.you.domain.model.vm.UserInfoResponse;
import top.pdev.you.domain.model.vm.UserProfileResponse;
import top.pdev.you.infrastructure.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 * Created in 2022/10/2 17:55
 *
 * @author Peter1303
 */
public interface UserService {
    User getUser(RoleEntity role);

    /**
     * 获取角色领域
     *
     * @param user 用户
     * @return {@link RoleEntity}
     */
    RoleEntity getRoleDomain(User user);

    /**
     * 登录
     *
     * @param vo VO
     * @return {@link LoginResultResponse}
     */
    LoginResultResponse login(UserLoginCommand vo);

    /**
     * 注册
     *
     * @param role 角色
     * @param vo   VO
     * @return {@link LoginResultResponse}
     */
    LoginResultResponse register(Role role, RegisterCommand vo);

    /**
     * 信息
     *
     * @param user 用户
     * @return {@link UserInfoResponse}
     */
    UserInfoResponse info(User user);

    /**
     * 资料
     *
     * @param user 用户
     * @return {@link UserProfileResponse}
     */
    UserProfileResponse profile(User user);

    /**
     * 权限到
     *
     * @param user       用户
     * @param permission 权限
     */
    void permissionTo(User user, Permission permission);

    /**
     * 设置资料
     *
     * @param user         用户
     * @param setProfileCommand 设置资料 VO
     */
    void setProfile(User user, SetProfileCommand setProfileCommand);

    /**
     * 删除账号
     *
     * @param tokenInfo 令牌信息
     * @param request   请求
     */
    void deleteAccount(User tokenInfo, HttpServletRequest request);

    /**
     * 获取用户
     *
     * @return {@link Result}<{@link ?}>
     */
    List<UserInfoResponse> getUsers();
}
