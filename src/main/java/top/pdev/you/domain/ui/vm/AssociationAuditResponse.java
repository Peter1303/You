package top.pdev.you.domain.ui.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import top.pdev.you.domain.ui.dto.StudentInfoDTO;

import java.time.LocalDateTime;

/**
 * 协会审计响应
 * 社团审核
 * Created in 2022/11/9 18:49
 *
 * @author Peter1303
 * @date 2023/04/26
 */
@Data
public class AssociationAuditResponse {
    private Long id;
    private String name;
    private StudentInfoDTO student;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
