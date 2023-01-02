package top.pdev.you.domain.factory;

import org.springframework.stereotype.Component;
import top.pdev.you.domain.entity.Like;

/**
 * 点赞工厂
 * Created in 2023/1/2 19:17
 *
 * @author Peter1303
 */
@Component
public class LikeFactory {
    /**
     * 新点赞领域
     *
     * @return {@link Like}
     */
    public Like newLike() {
        return new Like(null);
    }
}
