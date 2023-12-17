package top.pdev.you.domain.model.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 学生信息值对象
 * Created in 2023/1/5 15:07
 *
 * @author Peter1303
 */
@Data
public class StudentInfoResponse extends UserInfoResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long studentId;
}
