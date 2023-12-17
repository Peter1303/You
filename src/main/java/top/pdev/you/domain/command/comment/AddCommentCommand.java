package top.pdev.you.domain.command.comment;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.pdev.you.domain.command.IdCommand;

import javax.validation.constraints.NotNull;

/**
 * 添加评论命令
 * Created in 2023/1/2 21:00
 *
 * @author Peter1303
 */
@Data
public class AddCommentCommand extends IdCommand {
    @Length(min = 1, max = 255)
    @NotNull
    private String comment;
}
