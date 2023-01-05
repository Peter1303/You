package top.pdev.you.domain.service;

import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.IdVO;

/**
 * 社员服务
 * Created in 2023/1/4 22:18
 *
 * @author Peter1303
 */
public interface MemberService {
    /**
     * 列表
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    Result<?> list(User user);

    /**
     * 删除
     *
     * @param user 用户
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> remove(User user, IdVO idVO);
}
