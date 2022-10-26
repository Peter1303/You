package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 设置资料
 * Created in 2022/10/26 15:56
 *
 * @author Peter1303
 */
@Data
public class SetProfileVO {
    @Length(min = 1, max = 255)
    @NotNull
    private String contact;
}
