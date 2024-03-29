package top.pdev.you.domain.command.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.pdev.you.common.validator.intefaces.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * 注册命令
 * Created in 2022/10/3 13:48
 *
 * @author Peter1303
 */
@Data
public class RegisterCommand {
    @Length(min = 6, max = 64)
    @NotBlank(message = "临时凭证不可为空")
    private String code;

    @Positive(message = "班级 ID 错误", groups = {Student.class})
    private Long classId;

    @Length(min = 1, max = 64)
    private String no;

    @Length(min = 2, max = 32)
    private String name;

    @Length(min = 4, max = 255)
    private String contact;
}
