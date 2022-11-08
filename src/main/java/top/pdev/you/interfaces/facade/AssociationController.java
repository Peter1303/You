package top.pdev.you.interfaces.facade;

import org.springframework.validation.annotation.Validated;
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
import top.pdev.you.interfaces.model.vo.req.AddAssociationVO;
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
    @AccessPermission(permission = Permission.SUPER)
    @PutMapping("")
    public Result<?> add(@RequestBody @Validated AddAssociationVO addAssociationVO) {
        return associationService.add(addAssociationVO);
    }

    /**
     * 列表
     *
     * @param searchVO  搜索 VO
     * @param tokenInfo 令牌信息
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.MANAGER, lower = true)
    @PostMapping("")
    public Result<?> list(@RequestBody @Validated(Association.class) SearchVO searchVO,
                          @CurrentUser TokenInfo tokenInfo) {
        return associationService.list(searchVO, tokenInfo);
    }
}
