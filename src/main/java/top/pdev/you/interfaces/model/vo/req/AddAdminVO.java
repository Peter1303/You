package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 添加指导老师 VO
 * Created in 2022/11/15 18:34
 *
 * @author Peter1303
 */
@Data
public class AddAdminVO extends UidVO {
    @Min(1)
    @NotNull
    private Long associationId;
}
