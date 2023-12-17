package top.pdev.you.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 活动信息
 * Created in 2023/4/25 19:13
 *
 * @author Peter1303
 */
@Data
public class ActivityInfoDTO {
    private Long id;
    private String title;
    private String summary;
    private String detail;
    private String location;
    private Integer current;
    private Integer total;
    private Integer target;
    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    private TimeRangeDTO time;
    private AssociationBaseInfoDTO association;
}
