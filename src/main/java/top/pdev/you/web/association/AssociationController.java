package top.pdev.you.web.association;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.validator.intefaces.Association;
import top.pdev.you.domain.entity.User;
import top.pdev.you.application.service.association.AssociationService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.association.command.AddAssociationCommand;
import top.pdev.you.web.user.command.AddAdminCommand;
import top.pdev.you.web.association.command.ChangeNameCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.user.command.RemoveAdminCommand;
import top.pdev.you.web.query.command.SearchCommand;

import javax.annotation.Resource;

/**
 * 社团粗粒度
 * Created in 2022/11/7 21:15
 *
 * @author Peter1303
 */
@RequestMapping("/association")
@RestController
public class AssociationController {
    @Resource
    private AssociationService associationService;

    /**
     * 添加
     *
     * @param addAssociationCommand 添加社团 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.SUPER)
    @PostMapping("")
    public Result<?> add(@RequestBody @Validated AddAssociationCommand addAssociationCommand) {
        return associationService.add(addAssociationCommand);
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
        return associationService.delete(idCommand);
    }

    /**
     * 设置名字
     *
     * @param nameVO 名字 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.SUPER)
    @PutMapping("name")
    public Result<?> setName(@RequestBody @Validated ChangeNameCommand nameVO) {
        return associationService.setName(nameVO);
    }

    /**
     * 设置描述
     *
     * @param nameVO 名字 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.SUPER)
    @PutMapping("summary")
    public Result<?> setSummary(@RequestBody @Validated ChangeNameCommand nameVO) {
        return associationService.setSummary(nameVO);
    }

    /**
     * 列表
     *
     * @param user     用户
     * @param searchCommand 搜索 VO
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("")
    public Result<?> list(@CurrentUser User user,
                          @Validated(Association.class) SearchCommand searchCommand) {
        return associationService.list(user, searchCommand);
    }

    /**
     * 连接请求
     * 请求加入
     *
     * @param user 用户
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.MANAGER, lower = true)
    @PostMapping("join/request")
    public Result<?> joinRequest(@CurrentUser User user,
                                 @RequestBody @Validated IdCommand idCommand) {
        return associationService.join(false, user, idCommand);
    }

    /**
     * 审核列表
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.MANAGER)
    @GetMapping("audit")
    public Result<?> auditList(@CurrentUser User user) {
        return associationService.auditList(user);
    }

    /**
     * 通过
     *
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.MANAGER)
    @PutMapping("pass")
    public Result<?> pass(@RequestBody @Validated IdCommand idCommand) {
        return associationService.pass(idCommand);
    }

    /**
     * 拒绝
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.MANAGER)
    @PutMapping("reject")
    public Result<?> reject(@RequestBody @Validated IdCommand idCommand) {
        return associationService.reject(idCommand);
    }

    /**
     * 添加负责人
     *
     * @param addAdminVO 添加负责人 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.ADMIN)
    @PostMapping("manager")
    public Result<?> addManager(@RequestBody @Validated AddAdminCommand addAdminVO) {
        return associationService.addManager(addAdminVO);
    }

    /**
     * 删除负责人
     *
     * @param removeAdminVO 删除管理 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.ADMIN)
    @DeleteMapping("manager")
    public Result<?> removeManager(@RequestBody @Validated RemoveAdminCommand removeAdminVO) {
        return associationService.removeManager(removeAdminVO);
    }

    /**
     * 添加指导老师
     *
     * @param addAdminVO 添加指导老师 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.SUPER)
    @PostMapping("admin")
    public Result<?> addAdmin(@RequestBody @Validated AddAdminCommand addAdminVO) {
        return associationService.addAdmin(addAdminVO);
    }

    /**
     * 删除指导老师
     *
     * @param removeAdminVO 删除管理 VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.ADMIN)
    @DeleteMapping("admin")
    public Result<?> removeAdmin(@RequestBody @Validated RemoveAdminCommand removeAdminVO) {
        return associationService.removeAdmin(removeAdminVO);
    }
}