package top.pdev.you.interfaces.facade;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.SkipCheckLogin;
import top.pdev.you.common.validator.intefaces.Clazz;
import top.pdev.you.domain.service.ClassService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.NameVO;

import javax.annotation.Resource;

/**
 * 查询粗粒度
 * Created in 2022/10/3 18:27
 *
 * @author Peter1303
 */
@RequestMapping("/get")
@RestController
public class QueryController {
    @Resource
    private ClassService classService;

    @SkipCheckLogin
    @PostMapping("class")
    public Result<?> clazz(@RequestBody @Validated(Clazz.class) NameVO vo) {
        return classService.getClassList(vo);
    }
}
