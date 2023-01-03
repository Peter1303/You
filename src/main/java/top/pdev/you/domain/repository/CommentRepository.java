package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.entity.data.CommentDO;

import java.util.List;

/**
 * 评论仓库
 * Created in 2023/1/2 18:15
 *
 * @author Peter1303
 */
public interface CommentRepository extends IService<CommentDO> {
    /**
     * 通过 ID 查找
     *
     * @param commentId 评论 ID
     * @return {@link Comment}
     */
    Comment findById(Long commentId);

    /**
     * 通过帖子 ID 查找
     *
     * @param id ID
     * @return {@link List}<{@link Comment}>
     */
    List<Comment> findByPostId(Long id);

    /**
     * 通过帖子 ID 统计评论
     *
     * @param id ID
     * @return {@link Long}
     */
    Long countCommentByPostId(Long id);
}
