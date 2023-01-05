package top.pdev.you.interfaces.facade;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.validator.intefaces.Association;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.PostService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.ChangePostVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
import top.pdev.you.interfaces.model.vo.req.PostListVO;
import top.pdev.you.interfaces.model.vo.req.PostVO;

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
     * @param postVO 帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("post")
    public Result<?> post(@CurrentUser User user,
                          @RequestBody @Validated PostVO postVO) {
        return postService.post(user, postVO);
    }

    /**
     * 细节
     *
     * @param user 用户
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("")
    public Result<?> details(@CurrentUser User user,
                             @Validated IdVO idVO) {
        return postService.details(user, idVO);
    }


    /**
     * 发布社团帖子
     *
     * @param user   用户
     * @param postVO 帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("post/association")
    public Result<?> associationPost(@CurrentUser User user,
                                     @RequestBody @Validated(Association.class) PostVO postVO) {
        return postService.associationPost(user, postVO);
    }

    /**
     * 列表
     *
     * @param user       用户
     * @param postListVO 帖子列表 VO
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("list")
    public Result<?> list(@CurrentUser User user,
                          @Validated PostListVO postListVO) {
        return postService.list(user, postListVO);
    }

    /**
     * 用户帖子列表
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("list/user")
    public Result<?> listOfUser(@CurrentUser User user) {
        return postService.listOfUser(user);
    }

    /**
     * 删除
     *
     * @param user 用户
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("")
    public Result<?> delete(@CurrentUser User user,
                            @RequestBody @Validated IdVO idVO) {
        return postService.delete(user, idVO);
    }

    /**
     * 修改帖子
     *
     * @param changePostVO 修改帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("")
    public Result<?> changePost(@RequestBody @Validated ChangePostVO changePostVO) {
        return postService.changePost(changePostVO);
    }
}
