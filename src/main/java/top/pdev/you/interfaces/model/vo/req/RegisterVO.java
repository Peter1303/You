package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.pdev.you.common.validator.intefaces.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * 注册 VO
 * Created in 2022/10/3 13:48
 *
 * @author Peter1303
 */
@Data
public class RegisterVO {
    @Length(min = 6, max = 64)
    @NotBlank(message = "临时凭证不可为空")
    private String code;

    @Positive(message = "班级 ID 错误", groups = {Student.class})
    private Long classId;

    @Length(min = 1, max = 64)
    private String no;

    @Length(min = 2, max = 6)
    private String name;

    @Length(min = 1, max = 255)
    private String contact;
}
