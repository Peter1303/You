package top.pdev.you.web.command;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * ID 命令
 * Created in 2022/11/1 15:15
 *
 * @author Peter1303
 */
@Data
public class IdCommand {
    @NotNull
    @Positive
    private Long id;
}
