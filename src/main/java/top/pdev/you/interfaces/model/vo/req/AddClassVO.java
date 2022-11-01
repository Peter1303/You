package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 添加班级 VO
 * Created in 2022/11/1 15:26
 *
 * @author Peter1303
 */
@Data
public class AddClassVO {
    @NotNull
    @Length(min = 1, max = 60)
    private String name;

    @NotNull
    @Range(min = 2020)
    private Integer grade;

    @NotNull
    @Positive
    private Long campusId;

    @NotNull
    @Positive
    private Long instituteId;
}
