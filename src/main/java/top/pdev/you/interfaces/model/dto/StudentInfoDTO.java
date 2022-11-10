package top.pdev.you.interfaces.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 学生信息 DTO
 * Created in 2022/11/9 19:30
 *
 * @author Peter1303
 */
@Data
public class StudentInfoDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonProperty("class")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clazz;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String no;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String campus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String institute;
}
