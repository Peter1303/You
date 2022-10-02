package top.pdev.you.domain.entity.types;

import lombok.Data;

/**
 * 学院 ID
 * Created in 2022/10/1 16:47
 *
 * @author Peter1303
 */
@Data
public class InstituteId {
    private Long id;

    public InstituteId(Long id) {
        this.id = id;
    }
}
