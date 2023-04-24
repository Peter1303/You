package top.pdev.you.web.association.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 添加社团命令
 * Created in 2022/11/8 16:44
 *
 * @author Peter1303
 */
@Data
public class AddAssociationCommand {
    @NotNull
    @Length(min = 1, max = 50)
    private String name;

    @NotNull
    @Length(min = 1, max = 255)
    private String summary;
}
