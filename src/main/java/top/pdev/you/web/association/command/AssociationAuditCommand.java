package top.pdev.you.web.association.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import top.pdev.you.domain.ui.dto.StudentInfoDTO;

import java.time.LocalDateTime;

/**
 * 社团审核命令
 * Created in 2022/11/9 18:49
 *
 * @author Peter1303
 */
@Data
public class AssociationAuditCommand {
    private Long id;
    private String name;
    private StudentInfoDTO student;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
