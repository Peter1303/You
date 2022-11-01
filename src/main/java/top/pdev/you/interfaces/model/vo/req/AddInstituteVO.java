package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 添加学院 VO
 * Created in 2022/11/1 17:09
 *
 * @author Peter1303
 */
@Data
public class AddInstituteVO {
    @NotNull
    @Length(min = 1, max = 255)
    private String name;
}
