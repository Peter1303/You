package top.pdev.you.interfaces.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import top.pdev.you.interfaces.model.dto.AssociationNameDTO;

import java.util.List;

/**
 * 用户信息 VO
 * Created in 2022/10/19 10:35
 *
 * @author Peter1303
 */
@Data
public class UserInfoVO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String no;

    private String name;
    private Integer permission;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String association;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AssociationNameDTO> associations;
}
