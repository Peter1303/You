package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Post;

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
        return new Post();
    }
}
