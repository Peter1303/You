package top.pdev.you.domain.service.ai;

import top.pdev.you.domain.command.ai.AddKnowledgeCommand;
import top.pdev.you.domain.command.ai.AiAnswerCommand;
import top.pdev.you.domain.model.vm.AnswerResponse;

/**
 * AI 服务
 * Created in 2023/12/16 18:34
 *
 * @author Peter1303
 */
public interface AiService {
    /**
     * 问
     *
     * @param command 命令
     * @return {@link AnswerResponse}
     */
    AnswerResponse ask(AiAnswerCommand command);

    /**
     * 添加
     *
     * @param command 命令
     */
    void add(AddKnowledgeCommand command);
}
