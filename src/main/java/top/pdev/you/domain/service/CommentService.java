package top.pdev.you.domain.service;

import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddCommentVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;

/**
 * 评论服务
 * Created in 2023/1/2 20:58
 *
 * @author Peter1303
 */
public interface CommentService {
    /**
     * 帖子评论
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> postComments(IdVO idVO);

    /**
     * 添加
     *
     * @param user         用户
     * @param addCommentVO 添加评论 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(User user, AddCommentVO addCommentVO);

    /**
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdVO idVO);
}
