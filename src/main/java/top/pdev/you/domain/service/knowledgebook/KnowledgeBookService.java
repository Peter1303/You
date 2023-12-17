package top.pdev.you.domain.service.knowledgebook;

import top.pdev.you.domain.model.vm.KnowledgeBookResponse;
import top.pdev.you.web.knowledgebook.command.QuestionCommand;

/**
 * 知识库服务
 * Created in 2023/12/15 23:45
 *
 * @author Peter1303
 */
public interface KnowledgeBookService {
    /**
     * 问答
     * @param command 命令
     * @return 回答
     */
    KnowledgeBookResponse ask(QuestionCommand command);
}
