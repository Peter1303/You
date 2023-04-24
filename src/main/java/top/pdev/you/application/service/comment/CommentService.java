package top.pdev.you.application.service.comment;

import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.comment.command.AddCommentCommand;
import top.pdev.you.web.command.IdCommand;

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
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> postComments(IdCommand idCommand);

    /**
     * 添加
     *
     * @param user         用户
     * @param addCommentVO 添加评论 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(User user, AddCommentCommand addCommentVO);

    /**
     * 删除
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdCommand idCommand);
}
