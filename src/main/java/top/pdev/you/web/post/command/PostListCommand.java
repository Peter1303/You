package top.pdev.you.web.post.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;

/**
 * 帖子列表命令
 * Created in 2023/1/2 17:05
 *
 * @author Peter1303
 */
@Data
public class PostListCommand {
    @Positive
    private Long id;

    @Length(max = 255)
    private String search;
}
