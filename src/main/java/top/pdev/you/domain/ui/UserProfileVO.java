package top.pdev.you.domain.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 用户资料 VO
 * Created in 2022/10/26 10:07
 *
 * @author Peter1303
 */
@Data
public class UserProfileVO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String no;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @JsonProperty("class")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clazz;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String campus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String institute;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contact;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer grade;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer permission;
}
