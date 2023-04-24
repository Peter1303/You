package top.pdev.you.infrastructure.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Post;

import java.time.LocalDateTime;

/**
 * 帖子工厂
 * Created in 2023/1/2 15:18
 *
 * @author Peter1303
 */
@Component
public class PostFactory {
    /**
     * 新帖子领域
     *
     * @return {@link Post}
     */
    public Post newPost() {
        Post post = new Post();
        post.setTime(LocalDateTime.now());
        return post;
    }
}
