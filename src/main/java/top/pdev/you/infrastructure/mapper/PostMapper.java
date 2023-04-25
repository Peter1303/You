package top.pdev.you.infrastructure.mapper;

import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.ui.vm.PostInfoResponse;

import java.util.List;

/**
 * 帖子封装器
 * Created in 2023/1/2 17:41
 *
 * @author Peter1303
 */
public interface PostMapper {
    /**
     * 转换
     *
     * @param post 帖子
     * @return {@link PostInfoResponse}
     */
    PostInfoResponse convert(Post post);

    /**
     * 转换
     *
     * @param currentUser 当前用户
     * @param post        帖子
     * @return {@link PostInfoResponse}
     */
    PostInfoResponse convert(User currentUser, Post post);

    /**
     * 转换为简要列表
     *
     * @param posts 帖子
     * @param user  用户
     * @return {@link List}<{@link PostInfoResponse}>
     */
    List<PostInfoResponse> convertToBriefList(List<Post> posts, User user);
}
