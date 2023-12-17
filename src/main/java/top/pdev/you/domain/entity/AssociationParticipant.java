package top.pdev.you.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 社团成员
 * Created in 2022/10/1 14:13
 *
 * @author Peter1303
 */
@Data
public class AssociationParticipant {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 协团 ID
     */
    private Long associationId;

    /**
     * 学生 ID
     */
    private Long studentId;
}
