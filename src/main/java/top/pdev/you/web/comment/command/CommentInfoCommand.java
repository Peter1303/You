package top.pdev.you.web.comment.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论信息 VO
 * Created in 2023/1/2 22:27
 *
 * @author Peter1303
 */
@Data
public class CommentInfoCommand {
    private Long id;
    private String name;
    private String comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
