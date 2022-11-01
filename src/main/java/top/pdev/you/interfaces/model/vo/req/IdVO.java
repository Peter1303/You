package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * ID VO
 * Created in 2022/11/1 15:15
 *
 * @author Peter1303
 */
@Data
public class IdVO {
    @NotNull
    @Positive
    private Long id;
}
