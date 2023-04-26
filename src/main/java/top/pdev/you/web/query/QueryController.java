package top.pdev.you.web.query;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.SkipCheckLogin;
import top.pdev.you.common.validator.intefaces.Campus;
import top.pdev.you.common.validator.intefaces.Clazz;
import top.pdev.you.common.validator.intefaces.Institute;
import top.pdev.you.application.service.campus.CampusService;
import top.pdev.you.application.service.clazz.ClassService;
import top.pdev.you.application.service.institute.InstituteService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.query.command.SearchCommand;

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

    @Resource
    private CampusService campusService;

    @Resource
    private InstituteService instituteService;

    @SkipCheckLogin
    @GetMapping("class")
    public Result<?> clazz(@Validated(Clazz.class) SearchCommand vo) {
        return classService.getClassList(vo);
    }

    @SkipCheckLogin
    @GetMapping("campus")
    public Result<?> campus(@Validated(Campus.class) SearchCommand searchCommand) {
        return Result.ok(campusService.getCampusList(searchCommand));
    }

    @SkipCheckLogin
    @GetMapping("institute")
    public Result<?> institute(@Validated(Institute.class) SearchCommand searchCommand) {
        return instituteService.getInstituteList(searchCommand);
    }
}
