package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户 ID VO
 * Created in 2022/11/15 18:16
 *
 * @author Peter1303
 */
@Data
public class UidVO {
    @Min(1)
    @NotNull
    private Long uid;
}
