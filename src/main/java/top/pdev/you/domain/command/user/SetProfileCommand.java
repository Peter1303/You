package top.pdev.you.domain.command.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 设置资料命令
 * Created in 2022/10/26 15:56
 *
 * @author Peter1303
 */
@Data
public class SetProfileCommand {
    @Length(min = 4, max = 255)
    @NotNull
    private String contact;
}
