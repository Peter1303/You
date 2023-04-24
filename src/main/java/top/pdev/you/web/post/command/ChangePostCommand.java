package top.pdev.you.web.post.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.pdev.you.web.command.IdCommand;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 修改帖子命令
 * Created in 2023/1/2 18:53
 *
 * @author Peter1303
 */
@Data
public class ChangePostCommand extends IdCommand {
    @Positive
    private Long topicId;

    @Length(min = 1, max = 4096)
    @NotNull
    private String content;
}
