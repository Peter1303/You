package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.repository.LikeRepository;

/**
 * 点赞领域
 * Created in 2023/1/2 18:04
 *
 * @author Peter1303
 */
@TableName("`like`")
@Data
public class Like extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 帖子 ID
     */
    private Long postId;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 添加点赞
     *
     * @param user 用户
     * @param post 帖子
     */
    public void addLike(User user, Post post) {
        this.userId = user.getId();
        this.postId = post.getId();
        LikeRepository likeRepository = SpringUtil.getBean(LikeRepository.class);
        // 检查是否已经点过赞了
        if (likeRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            throw new BusinessException("已经点过赞了");
        }
        setPostId(postId);
        setUserId(userId);
        if (!likeRepository.save(this)) {
            throw new BusinessException("点赞失败");
        }
    }

    /**
     * 取消点赞
     */
    public void cancelLike() {
        LikeRepository likeRepository = SpringUtil.getBean(LikeRepository.class);
        if (!likeRepository.removeById(id)) {
            throw new BusinessException("取消点赞失败");
        }
    }
}
