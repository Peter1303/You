package top.pdev.you.domain.service;

import top.pdev.you.common.enums.Role;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.RegisterVO;
import top.pdev.you.interfaces.model.vo.req.UserLoginVO;

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
}
