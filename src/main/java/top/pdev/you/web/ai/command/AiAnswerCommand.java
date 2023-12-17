package top.pdev.you.web.ai.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Ai 回答命令
 * Created in 2023/12/16 18:36
 *
 * @author Peter1303
 */
@Data
public class AiAnswerCommand {
    @NotNull
    @Length(min = 4, max = 255)
    private String question;
}
