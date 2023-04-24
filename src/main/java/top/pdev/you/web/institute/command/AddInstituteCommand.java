package top.pdev.you.web.institute.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 添加学院命令
 * Created in 2022/11/1 17:09
 *
 * @author Peter1303
 */
@Data
public class AddInstituteCommand {
    @NotNull
    @Length(min = 1, max = 60)
    private String name;
}
