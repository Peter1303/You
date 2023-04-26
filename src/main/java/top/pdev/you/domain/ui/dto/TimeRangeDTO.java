package top.pdev.you.domain.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 时间段
 * Created in 2023/4/25 20:02
 *
 * @author Peter1303
 */
@Data
public class TimeRangeDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
}
