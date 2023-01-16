package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.repository.CommentRepository;

import java.time.LocalDateTime;

/**
 * 评论领域
 * Created in 2023/1/2 21:04
 *
 * @author Peter1303
 */
@TableName("comment")
@Data
public class Comment extends BaseEntity {
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
     * 评论
     */
    private String comment;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 保存
     *
     * @param post 帖子
     */
    public void save(Post post) {
        this.postId = post.getId();
        this.userId = post.getUserId();
        setComment(comment);
        setUserId(userId);
        setPostId(postId);
        setTime(LocalDateTime.now());
        CommentRepository commentRepository = SpringUtil.getBean(CommentRepository.class);
        if (!commentRepository.save(this)) {
            throw new BusinessException("无法评论");
        }
    }

    /**
     * 删除
     */
    public void delete() {
        CommentRepository commentRepository = SpringUtil.getBean(CommentRepository.class);
        if (!commentRepository.deleteById(this.id)) {
            throw new BusinessException("删除评论失败");
        }
    }
}
