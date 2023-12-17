package top.pdev.you.application.service.ai;

import top.pdev.you.domain.ui.vm.AnswerResponse;
import top.pdev.you.web.ai.command.AiAnswerCommand;

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
}
