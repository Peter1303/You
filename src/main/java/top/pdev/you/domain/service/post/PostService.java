package top.pdev.you.domain.service.post;

import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.post.ChangePostCommand;
import top.pdev.you.domain.command.post.PostCommand;
import top.pdev.you.domain.command.post.PostListCommand;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.model.vm.PostInfoResponse;

import java.util.List;

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
     */
    void post(User user, PostCommand postCommand);

    /**
     * 发布社团帖子
     *
     * @param user   用户
     * @param postCommand 帖子 VO
     */
    void associationPost(User user, PostCommand postCommand);

    /**
     * 细节
     *
     * @param user      用户
     * @param idCommand ID VO
     * @return {@link PostInfoResponse}
     */
    PostInfoResponse details(User user, IdCommand idCommand);

    /**
     * 列表
     *
     * @param user            用户
     * @param postListCommand 帖子列表 VO
     * @return {@link List}<{@link PostInfoResponse}>
     */
    List<PostInfoResponse> list(User user, PostListCommand postListCommand);

    /**
     * 用户帖子列表
     *
     * @param user 用户
     * @return {@link List}<{@link PostInfoResponse}>
     */
    List<PostInfoResponse> listOfUser(User user);

    /**
     * 删除
     *
     * @param user 用户
     * @param idCommand ID VO
     */
    void delete(User user, IdCommand idCommand);

    /**
     * 修改帖子
     *
     * @param changePostVO 修改帖子 VO
     */
    void changePost(ChangePostCommand changePostVO);
}
