package top.pdev.you.interfaces.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 社团信息 DTO
 * Created in 2022/10/24 19:07
 *
 * @author Peter1303
 */
@Data
public class AssociationInfoDTO extends AssociationBaseInfoDTO {
    @TableField("student_id")
    private Long studentId;

    private Integer numbers;
}
