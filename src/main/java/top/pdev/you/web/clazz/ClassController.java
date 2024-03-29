package top.pdev.you.web.clazz;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.clazz.AddClassCommand;
import top.pdev.you.domain.service.clazz.ClassService;
import top.pdev.you.infrastructure.result.Result;

import javax.annotation.Resource;

/**
 * 班级粗粒度
 * Created in 2022/11/1 15:18
 *
 * @author Peter1303
 */
@RequestMapping("/class")
@RestController
public class ClassController {
    @Resource
    private ClassService classService;

    /**
     * 添加
     *
     * @param addClassCommand 添加班级 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.SUPER)
    @PostMapping("")
    public Result<?> add(@RequestBody @Validated AddClassCommand addClassCommand) {
        classService.add(addClassCommand);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.SUPER)
    @DeleteMapping("")
    public Result<?> delete(@RequestBody @Validated IdCommand idCommand) {
        classService.delete(idCommand);
        return Result.ok();
    }
}
