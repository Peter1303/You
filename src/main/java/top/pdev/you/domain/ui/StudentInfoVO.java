package top.pdev.you.domain.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 学生信息 VO
 * Created in 2023/1/5 15:07
 *
 * @author Peter1303
 */
@Data
public class StudentInfoVO extends UserInfoVO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long studentId;
}
