package top.pdev.you.interfaces.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 帖子列表 VO
 * Created in 2023/1/2 17:13
 *
 * @author Peter1303
 */
@Data
public class PostInfoVO {
    private Long id;
    private String name;
    private String summary;
    private Long likes;
    private Long comments;
    private Boolean liked = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
