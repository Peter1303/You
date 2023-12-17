package top.pdev.you.domain.command.campus;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 添加校区命令
 * Created in 2022/11/1 19:06
 *
 * @author Peter1303
 */
@Data
public class AddCampusCommand {
    @NotNull
    @Length(min = 1, max = 60)
    private String name;
}
