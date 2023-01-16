package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Comment;

import java.util.List;

/**
 * 评论仓库
 * Created in 2023/1/2 18:15
 *
 * @author Peter1303
 */
public interface CommentRepository extends IService<Comment> {
    /**
     * 通过 ID 查找
     *
     * @param commentId 评论 ID
     * @return {@link Comment}
     */
    Comment findById(Long commentId);

    /**
     * 通过帖子 ID 查找，按照时间降序
     *
     * @param id ID
     * @return {@link List}<{@link Comment}>
     */
    List<Comment> findByPostIdOrderByTimeDesc(Long id);

    /**
     * 通过评论相似查找，按照时间降序排序
     *
     * @param content 内容
     * @return {@link List}<{@link Comment}>
     */
    List<Comment> findCommentContainingOrderByTimeDesc(String content);

    /**
     * 通过帖子 ID 统计评论
     *
     * @param id ID
     * @return {@link Long}
     */
    Long countCommentByPostId(Long id);

    /**
     * 通过 ID 删除
     *
     * @param id ID
     * @return boolean
     */
    boolean deleteById(Long id);
}
