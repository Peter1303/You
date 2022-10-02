package top.pdev.you.domain.entity.data;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 社团成员
 * Created in 2022/10/1 14:13
 *
 * @author Peter1303
 */
@TableName("association_participant")
@Data
public class AssociationParticipantDO {
    /**
     * 协团 ID
     */
    private Long associationId;

    /**
     * uid
     */
    private Long uid;

    /**
     * 时间
     */
    private LocalDateTime time;
}
