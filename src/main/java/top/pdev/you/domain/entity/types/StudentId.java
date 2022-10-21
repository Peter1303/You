package top.pdev.you.domain.entity.types;

import lombok.Data;

/**
 * 学生 ID
 * Created in 2022/10/19 10:49
 *
 * @author Peter1303
 */
@Data
public class StudentId {
    private Long id;

    public StudentId(Long id) {
        this.id = id;
    }
}
