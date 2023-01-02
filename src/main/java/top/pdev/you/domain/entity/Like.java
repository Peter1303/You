package top.pdev.you.domain.entity;

import lombok.Data;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.LikeDO;

import java.util.Optional;

/**
 * 点赞领域
 * Created in 2023/1/2 18:04
 *
 * @author Peter1303
 */
@Data
public class Like extends BaseEntity {
    private Long id;
    private Long postId;
    private Long userId;

    public Like(LikeDO likeDO) {
        if (!Optional.ofNullable(likeDO).isPresent()) {
            return;
        }
        this.id = likeDO.getId();
        this.postId = likeDO.getPostId();
        this.userId = likeDO.getUid();
    }

    /**
     * 添加点赞
     *
     * @param user 用户
     * @param post 帖子
     */
    public void addLike(User user, Post post) {
    }

    /**
     * 取消点赞
     *
     * @param user 用户
     * @param post 帖子
     */
    public void cancelLike(User user, Post post) {
    }
}
