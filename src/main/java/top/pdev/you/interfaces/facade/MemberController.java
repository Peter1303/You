package top.pdev.you.interfaces.facade;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.MemberService;
import top.pdev.you.infrastructure.result.Result;

import javax.annotation.Resource;

/**
 * 社员粗粒度
 * Created in 2023/1/4 22:04
 *
 * @author Peter1303
 */
@RequestMapping("/member")
@RestController
public class MemberController {
    @Resource
    private MemberService memberService;

    /**
     * 列表
     *
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.MANAGER, specified = true)
    @GetMapping("")
    public Result<?> list(@CurrentUser User user) {
        return memberService.list(user);
    }
}
