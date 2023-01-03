package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Like;

/**
 * 点赞仓库
 * Created in 2023/1/2 17:57
 *
 * @author Peter1303
 */
public interface LikeRepository extends IService<Like> {
    /**
     * 通过 ID 查找
     *
     * @param likeId 就像 ID
     * @return {@link Like}
     */
    Like findById(Long likeId);

    /**
     * 通过帖子 ID 统计点赞
     *
     * @param id ID
     * @return {@link Long}
     */
    Long countLikesByPostId(Long id);

    /**
     * 用户是否已经点赞
     *
     * @param userId 用户 ID
     * @param postId 帖子 ID
     * @return {@link Boolean}
     */
    Boolean existsByUserIdAndPostId(Long userId, Long postId);
}
