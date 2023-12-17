package top.pdev.you.domain.command.ai;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加知识库命令
 * Created in 2023/12/17 13:02
 *
 * @author Peter1303
 */
@Data
public class AddKnowledgeCommand {
    @NotNull
    @NotBlank
    private String knowledgeBookId;

    @NotNull
    @NotBlank
    private String point;

    @NotNull
    @NotBlank
    private String content;

    @NotNull
    @NotBlank
    private String source;
}
