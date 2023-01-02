package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.data.LikeDO;
import top.pdev.you.domain.entity.types.PostId;
import top.pdev.you.domain.entity.types.UserId;

/**
 * 点赞仓库
 * Created in 2023/1/2 17:57
 *
 * @author Peter1303
 */
public interface LikeRepository extends IService<LikeDO> {
    /**
     * 通过帖子 ID 统计点赞
     *
     * @param id ID
     * @return {@link Long}
     */
    Long countLikesByPostId(PostId id);

    /**
     * 用户是否已经点赞
     *
     * @param userId 用户 ID
     * @param postId 帖子 ID
     * @return {@link Boolean}
     */
    Boolean liked(UserId userId, PostId postId);
}
