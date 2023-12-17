package top.pdev.you.domain.command;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 用户 ID 命令
 * Created in 2022/11/15 18:16
 *
 * @author Peter1303
 */
@Data
public class UidCommand {
    @Min(1)
    @NotNull
    private Long uid;
}
