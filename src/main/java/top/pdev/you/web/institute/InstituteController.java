package top.pdev.you.web.institute;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.institute.AddInstituteCommand;
import top.pdev.you.domain.service.institute.InstituteService;
import top.pdev.you.infrastructure.result.Result;

import javax.annotation.Resource;

/**
 * 学院粗粒度
 * Created in 2022/11/1 17:05
 *
 * @author Peter1303
 */
@RequestMapping("/institute")
@RestController
public class InstituteController {
    @Resource
    private InstituteService instituteService;

    @AccessPermission(permission = Permission.SUPER)
    @PostMapping("")
    public Result<?> add(@RequestBody @Validated AddInstituteCommand addInstituteCommand) {
        instituteService.add(addInstituteCommand);
        return Result.ok();
    }

    @AccessPermission(permission = Permission.SUPER)
    @DeleteMapping("")
    public Result<?> delete(@RequestBody @Validated IdCommand idCommand) {
        instituteService.delete(idCommand);
        return Result.ok();
    }
}
