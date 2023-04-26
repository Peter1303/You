package top.pdev.you.domain.ui.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 社团信息
 * Created in 2022/11/8 22:33
 *
 * @author Peter1303
 */
@Data
public class AssociationInfoResponse {
    private Long id;
    private String name;
    private String summary;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;
    private Long numbers;
}
