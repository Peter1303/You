package top.pdev.you.web.campus;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.application.service.campus.CampusService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.campus.command.AddCampusCommand;
import top.pdev.you.web.command.IdCommand;

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
        return campusService.add(addCampusCommand);
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
        return campusService.delete(idCommand);
    }
}
