package top.pdev.you.web.user.command;

import lombok.Data;
import top.pdev.you.web.command.UidCommand;

import javax.validation.constraints.Min;

/**
 * 添加指导老师命令
 * Created in 2022/11/15 18:34
 *
 * @author Peter1303
 */
@Data
public class RemoveAdminCommand extends UidCommand {
    @Min(1)
    private Long associationId;
}
