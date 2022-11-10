package top.pdev.you.interfaces.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created in 2022/11/9 18:40
 *
 * @author Peter1303
 */
@Data
public class AssociationAuditDTO {
    private Long id;
    private String name;

    @TableField("student_id")
    private Long studentId;

    private LocalDateTime time;
}
