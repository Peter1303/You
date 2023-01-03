package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.CommentDO;
import top.pdev.you.domain.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 评论领域
 * Created in 2023/1/2 21:04
 *
 * @author Peter1303
 */
@Data
public class Comment extends BaseEntity {
    private Long id;
    private Long postId;
    private Long userId;
    private String comment;
    private LocalDateTime time;

    @Getter(AccessLevel.NONE)
    private CommentDO commentDO;

    @Getter(AccessLevel.NONE)
    private final CommentRepository commentRepository = SpringUtil.getBean(CommentRepository.class);

    public Comment(CommentDO commentDO) {
        if (!Optional.ofNullable(commentDO).isPresent()) {
            return;
        }
        this.commentDO = commentDO;
        this.id = commentDO.getId();
        this.userId = commentDO.getUid();
        this.postId = commentDO.getPostId();
        this.comment = commentDO.getComment();
        this.time = commentDO.getTime();
    }

    /**
     * 保存
     *
     * @param post 帖子
     */
    public void save(Post post) {
        this.postId = post.getId();
        this.userId = post.getUserId();
        this.commentDO = new CommentDO();
        commentDO.setComment(comment);
        commentDO.setUid(userId);
        commentDO.setPostId(postId);
        commentDO.setTime(LocalDateTime.now());
        if (!commentRepository.save(commentDO)) {
            throw new BusinessException("无法评论");
        }
    }

    /**
     * 删除
     */
    public void delete() {
        if (!commentRepository.removeById(this.id)) {
            throw new BusinessException("删除评论失败");
        }
    }
}
