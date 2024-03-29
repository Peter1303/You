package top.pdev.you.web.campus;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.campus.AddCampusCommand;
import top.pdev.you.domain.service.campus.CampusService;
import top.pdev.you.infrastructure.result.Result;

import javax.annotation.Resource;

/**
 * 校区粗粒度
 * Created in 2022/11/1 18:59
 *
 * @author Peter1303
 */
@RequestMapping("/campus")
@RestController
public class CampusController {
    @Resource
    private CampusService campusService;

    /**
     * 添加
     *
     * @param addCampusCommand 添加校区 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.SUPER)
    @PostMapping("")
    public Result<?> add(@RequestBody @Validated AddCampusCommand addCampusCommand) {
        campusService.add(addCampusCommand);
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
        campusService.delete(idCommand);
        return Result.ok();
    }
}
