package top.pdev.you.domain.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime startTime;

    @JsonProperty("end")
    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime endTime;
}
