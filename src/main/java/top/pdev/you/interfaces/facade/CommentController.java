package top.pdev.you.interfaces.facade;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.CommentService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddCommentVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;

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
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("post")
    public Result<?> postComments(@Validated IdVO idVO) {
        return commentService.postComments(idVO);
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
                         @RequestBody @Validated AddCommentVO addCommentVO) {
        return commentService.add(user, addCommentVO);
    }

    /**
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    @DeleteMapping("")
    public Result<?> delete(@RequestBody @Validated IdVO idVO) {
        return commentService.delete(idVO);
    }
}
