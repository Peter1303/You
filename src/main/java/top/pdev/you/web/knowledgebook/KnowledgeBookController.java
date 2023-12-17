package top.pdev.you.web.knowledgebook;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.command.knowledgebook.QuestionCommand;
import top.pdev.you.domain.model.vm.KnowledgeBookResponse;
import top.pdev.you.domain.service.knowledgebook.KnowledgeBookService;
import top.pdev.you.infrastructure.result.Result;

import javax.annotation.Resource;

/**
 * 知识库
 * Created in 2023/12/15 23:47
 *
 * @author Peter1303
 */
@RequestMapping("/knowledge-book")
@RestController
public class KnowledgeBookController {
    @Resource
    private KnowledgeBookService knowledgeBookService;

    @AccessPermission(permission = Permission.MANAGER)
    @GetMapping
    public Result<?> ask(@Validated QuestionCommand command) {
        KnowledgeBookResponse response = knowledgeBookService.ask(command);
        return Result.ok(response);
    }
}
