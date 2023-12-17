package top.pdev.you.application.service.comment;

import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.ui.dto.CommentInfoDTO;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.comment.command.AddCommentCommand;

import java.util.List;

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
     * @return {@link List}<{@link CommentInfoDTO}>
     */
    List<CommentInfoDTO> postComments(IdCommand idCommand);

    /**
     * 添加
     *
     * @param user         用户
     * @param addCommentVO 添加评论 VO
     */
    void add(User user, AddCommentCommand addCommentVO);

    /**
     * 删除
     *
     * @param idCommand ID VO
     */
    void delete(IdCommand idCommand);
}
