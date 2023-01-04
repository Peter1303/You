package top.pdev.you.interfaces.facade;

import org.springframework.transaction.annotation.Transactional;
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
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.validator.intefaces.Association;
import top.pdev.you.domain.service.AssociationService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddAdminVO;
import top.pdev.you.interfaces.model.vo.req.AddAssociationVO;
import top.pdev.you.interfaces.model.vo.req.ChangeNameVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
import top.pdev.you.interfaces.model.vo.req.RemoveAdminVO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

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
     * @param addAssociationVO 添加社团 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.SUPER)
    @PostMapping("")
    public Result<?> add(@RequestBody @Validated AddAssociationVO addAssociationVO) {
        return associationService.add(addAssociationVO);
    }

    /**
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.SUPER)
    @DeleteMapping("")
    public Result<?> delete(@RequestBody @Validated IdVO idVO) {
        return associationService.delete(idVO);
    }

    /**
     * 设置名字
     *
     * @param nameVO 名字 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.SUPER)
    @PutMapping("name")
    public Result<?> setName(@RequestBody @Validated ChangeNameVO nameVO) {
        return associationService.setName(nameVO);
    }

    /**
     * 设置描述
     *
     * @param nameVO 名字 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.SUPER)
    @PutMapping("summary")
    public Result<?> setSummary(@RequestBody @Validated ChangeNameVO nameVO) {
        return associationService.setSummary(nameVO);
    }

    /**
     * 列表
     *
     * @param searchVO  搜索 VO
     * @param tokenInfo 令牌信息
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("")
    public Result<?> list(@Validated(Association.class) SearchVO searchVO,
                          @CurrentUser TokenInfo tokenInfo) {
        return associationService.list(searchVO, tokenInfo);
    }

    /**
     * 请求加入
     *
     * @param idVO      ID VO
     * @param tokenInfo 令牌信息
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.MANAGER, lower = true)
    @PostMapping("join/request")
    public Result<?> joinRequest(@RequestBody @Validated IdVO idVO,
                                 @CurrentUser TokenInfo tokenInfo) {
        return associationService.join(false, tokenInfo, idVO);
    }

    /**
     * 审核列表
     *
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.MANAGER)
    @GetMapping("audit")
    public Result<?> auditList() {
        return associationService.auditList();
    }

    /**
     * 通过
     *
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.MANAGER)
    @PutMapping("pass")
    public Result<?> pass(@RequestBody @Validated IdVO idVO) {
        return associationService.pass(idVO);
    }

    /**
     * 拒绝
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.MANAGER)
    @PutMapping("reject")
    public Result<?> reject(@RequestBody @Validated IdVO idVO) {
        return associationService.reject(idVO);
    }

    /**
     * 添加负责人
     *
     * @param addAdminVO 添加负责人 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.ADMIN)
    @PostMapping("manager")
    public Result<?> addManager(@RequestBody @Validated AddAdminVO addAdminVO) {
        return associationService.addManager(addAdminVO);
    }

    /**
     * 删除负责人
     *
     * @param removeAdminVO 删除管理 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.ADMIN)
    @DeleteMapping("manager")
    public Result<?> removeManager(@RequestBody @Validated RemoveAdminVO removeAdminVO) {
        return associationService.removeManager(removeAdminVO);
    }

    /**
     * 添加指导老师
     *
     * @param addAdminVO 添加指导老师 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.SUPER)
    @PostMapping("admin")
    public Result<?> addAdmin(@RequestBody @Validated AddAdminVO addAdminVO) {
        return associationService.addAdmin(addAdminVO);
    }

    /**
     * 删除指导老师
     *
     * @param removeAdminVO 删除管理 VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @AccessPermission(permission = Permission.ADMIN)
    @DeleteMapping("admin")
    public Result<?> removeAdmin(@RequestBody @Validated RemoveAdminVO removeAdminVO) {
        return associationService.removeAdmin(removeAdminVO);
    }
}
