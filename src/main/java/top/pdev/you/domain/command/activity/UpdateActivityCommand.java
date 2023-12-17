package top.pdev.you.domain.command.activity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 更新活动命令
 * Created in 2023/4/24 20:08
 *
 * @author Peter1303
 */
@Data
public class UpdateActivityCommand extends AddActivityCommand {
    @Min(1)
    @NotNull
    private Long id;
}
