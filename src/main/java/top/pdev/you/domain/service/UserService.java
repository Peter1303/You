package top.pdev.you.domain.service;

import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.enums.Role;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.RegisterVO;
import top.pdev.you.interfaces.model.vo.req.SetProfileVO;
import top.pdev.you.interfaces.model.vo.req.UserLoginVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 * Created in 2022/10/2 17:55
 *
 * @author Peter1303
 */
public interface UserService {
    /**
     * 登录
     *
     * @param vo VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> login(UserLoginVO vo);

    /**
     * 注册
     *
     * @param role 角色
     * @param vo   VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> register(Role role, RegisterVO vo);

    /**
     * 信息
     *
     * @param tokenInfo 令牌信息
     * @return {@link Result}<{@link ?}>
     */
    Result<?> info(TokenInfo tokenInfo);

    /**
     * 资料
     *
     * @param tokenInfo 令牌信息
     * @return {@link Result}<{@link ?}>
     */
    Result<?> profile(TokenInfo tokenInfo);

    /**
     * 设置资料
     *
     * @param tokenInfo    令牌信息
     * @param setProfileVO 设置资料 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> setProfile(TokenInfo tokenInfo, SetProfileVO setProfileVO);

    /**
     * 删除账号
     *
     * @param tokenInfo 令牌信息
     * @param request   请求
     * @return {@link Result}<{@link ?}>
     */
    Result<?> deleteAccount(TokenInfo tokenInfo, HttpServletRequest request);

    /**
     * 获取用户
     *
     * @return {@link Result}<{@link ?}>
     */
    Result<?> getUsers();
}
