package top.pdev.you.web.knowledgebook.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 问题命令
 * Created in 2023/12/15 23:48
 *
 * @author Peter1303
 */
@Data
public class QuestionCommand {
    @NotNull
    @Length(min = 4, max = 255)
    private String question;
}
