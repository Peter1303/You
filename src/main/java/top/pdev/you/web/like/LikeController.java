package top.pdev.you.web.like;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.like.LikeService;
import top.pdev.you.infrastructure.result.Result;

import javax.annotation.Resource;

/**
 * 点赞粗粒度
 * Created in 2023/1/2 19:11
 *
 * @author Peter1303
 */
@RequestMapping("/like")
@RestController
public class LikeController {
    @Resource
    private LikeService likeService;

    /**
     * 添加
     *
     * @param user 用户
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @PostMapping("")
    public Result<?> add(@CurrentUser User user,
                         @RequestBody @Validated IdCommand idCommand) {
        likeService.add(user, idCommand);
        return Result.ok();
    }

    /**
     * 取消
     *
     * @param idCommand ID VO
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @DeleteMapping("")
    public Result<?> cancel(@CurrentUser User user,
                            @RequestBody @Validated IdCommand idCommand) {
        likeService.delete(user, idCommand);
        return Result.ok();
    }
}
