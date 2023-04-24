package top.pdev.you.application.service.post;

import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.post.command.ChangePostCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.post.command.PostListCommand;
import top.pdev.you.web.post.command.PostCommand;

/**
 * 帖子服务
 * Created in 2023/1/2 13:35
 *
 * @author Peter1303
 */
public interface PostService {
    /**
     * 发布帖子
     *
     * @param user   用户
     * @param postCommand 帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> post(User user, PostCommand postCommand);

    /**
     * 发布社团帖子
     *
     * @param user   用户
     * @param postCommand 帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> associationPost(User user, PostCommand postCommand);

    /**
     * 细节
     *
     * @param user 用户
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> details(User user, IdCommand idCommand);

    /**
     * 列表
     *
     * @param user       用户
     * @param postListCommand 帖子列表 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> list(User user, PostListCommand postListCommand);

    /**
     * 用户帖子列表
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    Result<?> listOfUser(User user);

    /**
     * 删除
     *
     * @param user 用户
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(User user, IdCommand idCommand);

    /**
     * 修改帖子
     *
     * @param changePostVO 修改帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> changePost(ChangePostCommand changePostVO);
}