package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import top.pdev.you.interfaces.model.vo.base.NameBaseVO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 修改名字 VO
 * Created in 2022/12/28 9:30
 *
 * @author Peter1303
 */
@Data
public class ChangeNameVO extends NameBaseVO {
    @NotNull
    @Min(1)
    private Long id;
}
