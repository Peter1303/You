package top.pdev.you.domain.command.association;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 修改名字命令
 * Created in 2022/12/28 9:30
 *
 * @author Peter1303
 */
@Data
public class ChangeNameCommand {
    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    @Length(min = 1, max = 255)
    private String name;
}
