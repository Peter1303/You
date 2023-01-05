package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 协会参与者 DO
 * 社团成员
 * Created in 2022/10/1 14:13
 *
 * @author Peter1303
 * @date 2023/01/05
 */
@TableName("association_participant")
@Data
public class AssociationParticipantDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 协团 ID
     */
    private Long associationId;

    /**
     * 学生 ID
     */
    @TableField("student_id")
    private Long studentId;
}
