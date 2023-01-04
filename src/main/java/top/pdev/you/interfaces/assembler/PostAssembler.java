package top.pdev.you.interfaces.assembler;

import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.interfaces.model.vo.PostInfoVO;

/**
 * 帖子封装器
 * Created in 2023/1/2 17:41
 *
 * @author Peter1303
 */
public interface PostAssembler {
    /**
     * 转换
     *
     * @param post 帖子
     * @return {@link PostInfoVO}
     */
    PostInfoVO convert(Post post);

    /**
     * 转换
     *
     * @param currentUser 当前用户
     * @param post        帖子
     * @return {@link PostInfoVO}
     */
    PostInfoVO convert(User currentUser, Post post);
}
