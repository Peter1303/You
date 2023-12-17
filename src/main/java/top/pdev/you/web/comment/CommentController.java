package top.pdev.you.web.comment;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.application.service.comment.CommentService;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.comment.command.AddCommentCommand;

import javax.annotation.Resource;

/**
 * 评论粗粒度
 * Created in 2023/1/2 20:57
 *
 * @author Peter1303
 */
@RequestMapping("/comment")
@RestController
public class CommentController {
    @Resource
    private CommentService commentService;

    /**
     * 帖子评论
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("post")
    public Result<?> postComments(@Validated IdCommand idCommand) {
        return Result.ok(commentService.postComments(idCommand));
    }

    /**
     * 添加
     *
     * @param user         用户
     * @param addCommentVO 添加评论 VO
     * @return {@link Result}<{@link ?}>
     */
    @PostMapping("")
    public Result<?> add(@CurrentUser User user,
                         @RequestBody @Validated AddCommentCommand addCommentVO) {
        commentService.add(user, addCommentVO);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @DeleteMapping("")
    public Result<?> delete(@RequestBody @Validated IdCommand idCommand) {
        commentService.delete(idCommand);
        return Result.ok();
    }
}
