package top.pdev.you.infrastructure.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Comment;

/**
 * 评论工厂
 * Created in 2023/1/2 21:29
 *
 * @author Peter1303
 */
@Component
public class CommentFactory {
    /**
     * 新评论领域
     *
     * @return {@link Comment}
     */
    public Comment newComment() {
        return new Comment();
    }
}
