package top.pdev.you.domain.command.user;

import lombok.Data;
import top.pdev.you.domain.command.UidCommand;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 添加指导老师命令
 * Created in 2022/11/15 18:34
 *
 * @author Peter1303
 */
@Data
public class AddAdminCommand extends UidCommand {
    @Min(1)
    @NotNull
    private Long associationId;
}
