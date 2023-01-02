package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.data.CommentDO;
import top.pdev.you.domain.entity.types.PostId;

/**
 * 评论仓库
 * Created in 2023/1/2 18:15
 *
 * @author Peter1303
 */
public interface CommentRepository extends IService<CommentDO> {
    /**
     * 通过帖子 ID 统计评论
     *
     * @param id ID
     * @return {@link Long}
     */
    Long countCommentByPostId(PostId id);
}
