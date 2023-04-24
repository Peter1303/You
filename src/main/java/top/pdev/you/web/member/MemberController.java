package top.pdev.you.web.member;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.AccessPermission;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.entity.User;
import top.pdev.you.application.service.member.MemberService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.command.IdCommand;

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

    /**
     * 删除
     *
     * @param user 用户
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @AccessPermission(permission = Permission.MANAGER, specified = true)
    @DeleteMapping("")
    public Result<?> remove(@CurrentUser User user,
                            @RequestBody @Validated IdCommand idCommand) {
        return memberService.remove(user, idCommand);
    }
}
