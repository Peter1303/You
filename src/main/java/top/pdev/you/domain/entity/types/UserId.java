package top.pdev.you.domain.entity.types;

import lombok.Data;

/**
 * 用户 ID
 * Created in 2022/10/1 16:47
 *
 * @author Peter1303
 */
@Data
public class UserId {
    private Long id;

    public UserId(Long id) {
        this.id = id;
    }
}
