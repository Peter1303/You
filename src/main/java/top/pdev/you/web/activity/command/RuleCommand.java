package top.pdev.you.web.activity.command;

import lombok.Data;
import top.pdev.you.web.command.TimeCommand;

/**
 * 规则命令
 * Created in 2023/4/25 18:34
 *
 * @author Peter1303
 */
@Data
public class RuleCommand {
    private Integer target;
    private TimeCommand timeCommand;
    private Integer total;
}
