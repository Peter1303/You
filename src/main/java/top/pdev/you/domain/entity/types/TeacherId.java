package top.pdev.you.domain.entity.types;

import lombok.Data;

/**
 * 老师 ID
 * Created in 2022/10/19 10:49
 *
 * @author Peter1303
 */
@Data
public class TeacherId {
    private Long id;

    public TeacherId(Long id) {
        this.id = id;
    }
}
