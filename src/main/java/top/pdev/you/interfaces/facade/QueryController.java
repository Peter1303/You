package top.pdev.you.interfaces.facade;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.SkipCheckLogin;
import top.pdev.you.common.validator.intefaces.Campus;
import top.pdev.you.common.validator.intefaces.Clazz;
import top.pdev.you.common.validator.intefaces.Institute;
import top.pdev.you.domain.service.CampusService;
import top.pdev.you.domain.service.ClassService;
import top.pdev.you.domain.service.InstituteService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

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
    @PostMapping("class")
    public Result<?> clazz(@RequestBody @Validated(Clazz.class) SearchVO vo) {
        return classService.getClassList(vo);
    }

    @SkipCheckLogin
    @PostMapping("campus")
    public Result<?> campus(@RequestBody @Validated(Campus.class) SearchVO searchVO) {
        return campusService.getCampusList(searchVO);
    }

    @SkipCheckLogin
    @PostMapping("institute")
    public Result<?> institute(@RequestBody @Validated(Institute.class) SearchVO searchVO) {
        return instituteService.getInstituteList(searchVO);
    }
}
