package top.pdev.you.web.post.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.pdev.you.common.validator.intefaces.Association;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 发布帖子命令
 * Created in 2023/1/2 13:38
 *
 * @author Peter1303
 */
@Data
public class PostCommand {
    @Positive(groups = {Association.class})
    private Long associationId;

    @Positive
    private Long topicId;

    @Length(min = 1, max = 4096)
    @NotNull
    private String content;
}
