package top.pdev.you.interfaces.model.vo.base;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 名称基类
 * Created in 2022/12/28 8:56
 *
 * @author Peter1303
 */
@Data
public class NameBaseVO {
    @NotNull
    @Length(min = 1, max = 255)
    private String name;
}
