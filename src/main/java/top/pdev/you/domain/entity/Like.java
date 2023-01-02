package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.LikeDO;
import top.pdev.you.domain.entity.types.PostId;
import top.pdev.you.domain.repository.LikeRepository;

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

    @Getter(AccessLevel.NONE)
    private final LikeRepository likeRepository = SpringUtil.getBean(LikeRepository.class);

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
        this.userId = user.getUserId().getId();
        this.postId = post.getId();
        // 检查是否已经点过赞了
        if (likeRepository.liked(user.getUserId(), new PostId(postId))) {
            throw new BusinessException("已经点过赞了");
        }
        LikeDO likeDO = new LikeDO();
        likeDO.setPostId(postId);
        likeDO.setUid(userId);
        if (!likeRepository.save(likeDO)) {
            throw new BusinessException("点赞失败");
        }
    }

    /**
     * 取消点赞
     */
    public void cancelLike() {
        if (!likeRepository.removeById(id)) {
            throw new BusinessException("取消点赞失败");
        }
    }
}
