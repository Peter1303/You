package top.pdev.you.web.ai;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.domain.command.ai.AiAnswerCommand;
import top.pdev.you.domain.service.ai.AiService;
import top.pdev.you.infrastructure.result.Result;

import javax.annotation.Resource;

/**
 * AI 控制器
 * Created in 2023/12/16 18:35
 *
 * @author Peter1303
 */
@RequestMapping("/ai")
@RestController
public class AiController {
    @Resource
    private AiService aiService;

    @GetMapping
    public Result<?> ai(@Validated AiAnswerCommand command) {
        return Result.ok(aiService.ask(command));
    }
}
