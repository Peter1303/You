package top.pdev.you.interfaces.facade;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.SkipCheckLogin;
import top.pdev.you.domain.service.UserService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.UserLoginVO;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户粗粒度
 * Created in 2022/9/30 22:30
 *
 * @author Peter1303
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 初始化
     *
     * @param vo VO
     * @return {@link Result}<{@link ?}>
     */
    @SkipCheckLogin
    @PostMapping("init")
    public Result<?> init(@RequestBody @Valid UserLoginVO vo) {
        return userService.addSuperAdmin(vo);
    }

    /**
     * 登录
     *
     * @param vo VO
     * @return {@link Result}<{@link ?}>
     */
    @SkipCheckLogin
    @PostMapping("login")
    public Result<?> login(@RequestBody @Valid UserLoginVO vo) {
        return userService.login(vo);
    }
}
