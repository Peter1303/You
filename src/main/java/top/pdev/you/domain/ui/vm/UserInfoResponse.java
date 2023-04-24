package top.pdev.you.domain.ui.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import top.pdev.you.domain.ui.dto.AssociationBaseInfoDTO;

import java.util.List;

/**
 * 用户信息 VO
 * Created in 2022/10/19 10:35
 *
 * @author Peter1303
 */
@Data
public class UserInfoResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String no;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer permission;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String contact;

    @JsonProperty("class")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String clazz;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String association;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String institute;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String campus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AssociationBaseInfoDTO> associations;
}
