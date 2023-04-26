package top.pdev.you.web.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 时间命令
 * Created in 2023/4/24 20:10
 *
 * @author Peter1303
 */
@Data
public class TimeCommand {
    @JsonProperty("start")
    @NotNull
    private String startTime;

    @JsonProperty("end")
    @NotNull
    private String endTime;
}
