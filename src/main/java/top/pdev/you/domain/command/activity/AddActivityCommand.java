package top.pdev.you.domain.command.activity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.pdev.you.domain.command.TimeCommand;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 添加活动命令
 * Created in 2023/4/24 20:08
 *
 * @author Peter1303
 */
@Data
public class AddActivityCommand {
    @NotNull
    private String title;

    @Length(max = 30)
    @NotNull
    private String summary;

    @NotNull
    private String detail;

    @NotNull
    private String location;

    @Max(13000)
    @NotNull
    private Integer target;

    @Min(1)
    @NotNull
    private Integer total;

    @NotNull
    private TimeCommand time;
}
