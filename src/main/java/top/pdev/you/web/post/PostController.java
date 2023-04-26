package top.pdev.you.web.post;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.application.service.post.PostService;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.validator.intefaces.Association;
import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.post.command.ChangePostCommand;
import top.pdev.you.web.post.command.PostCommand;
import top.pdev.you.web.post.command.PostListCommand;

import javax.annotation.Resource;

/**
 * 帖子控制器
 * Created in 2023/1/2 13:18
 *
 * @author Peter1303
 */
@RequestMapping("/post")
@RestController
public class PostController {
    @Resource
    private PostService postService;

    /**
     * 帖子
     *
     * @param user   用户
     * @param postCommand 帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    @PostMapping("post")
    public Result<?> post(@CurrentUser User user,
                          @RequestBody @Validated PostCommand postCommand) {
        postService.post(user, postCommand);
        return Result.ok();
    }

    /**
     * 细节
     *
     * @param user 用户
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("")
    public Result<?> details(@CurrentUser User user,
                             @Validated IdCommand idCommand) {
        return Result.ok(postService.details(user, idCommand));
    }


    /**
     * 发布社团帖子
     *
     * @param user   用户
     * @param postCommand 帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    @PostMapping("post/association")
    public Result<?> associationPost(@CurrentUser User user,
                                     @RequestBody @Validated(Association.class) PostCommand postCommand) {
        postService.associationPost(user, postCommand);
        return Result.ok();
    }

    /**
     * 列表
     *
     * @param user       用户
     * @param postListCommand 帖子列表 VO
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("list")
    public Result<?> list(@CurrentUser User user,
                          @Validated PostListCommand postListCommand) {
        return Result.ok(postService.list(user, postListCommand));
    }

    /**
     * 用户帖子列表
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("list/user")
    public Result<?> listOfUser(@CurrentUser User user) {
        return Result.ok(postService.listOfUser(user));
    }

    /**
     * 删除
     *
     * @param user 用户
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @DeleteMapping("")
    public Result<?> delete(@CurrentUser User user,
                            @RequestBody @Validated IdCommand idCommand) {
        postService.delete(user, idCommand);
        return Result.ok();
    }

    /**
     * 修改帖子
     *
     * @param changePostVO 修改帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    @PutMapping("")
    public Result<?> changePost(@RequestBody @Validated ChangePostCommand changePostVO) {
        postService.changePost(changePostVO);
        return Result.ok();
    }
}
