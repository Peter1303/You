package top.pdev.you.web.user;

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
import top.pdev.you.common.annotation.SkipCheckLogin;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.enums.Role;
import top.pdev.you.common.validator.intefaces.Student;
import top.pdev.you.common.validator.intefaces.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.application.service.user.UserService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.user.command.RegisterCommand;
import top.pdev.you.web.user.command.SetProfileCommand;
import top.pdev.you.web.user.command.UserLoginCommand;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
     * 登录
     *
     * @param vo VO
     * @return {@link Result}<{@link ?}>
     */
    @SkipCheckLogin
    @PutMapping("login")
    public Result<?> login(@RequestBody @Valid UserLoginCommand vo) {
        return userService.login(vo);
    }

    /**
     * 注册老师
     *
     * @param vo VO
     * @return {@link Result}<{@link ?}>
     */
    @SkipCheckLogin
    @PostMapping("register/teacher")
    public Result<?> registerOfTeacher(@RequestBody @Validated(Teacher.class) RegisterCommand vo) {
        return userService.register(Role.TEACHER, vo);
    }

    /**
     * 注册学生
     *
     * @param vo VO
     * @return {@link Result}<{@link ?}>
     */
    @SkipCheckLogin
    @PostMapping("register/student")
    public Result<?> registerOfStudent(@RequestBody @Validated(Student.class) RegisterCommand vo) {
        return userService.register(Role.STUDENT, vo);
    }

    @AccessPermission(permission = Permission.ADMIN)
    @GetMapping("")
    public Result<?> getUsers() {
        return userService.getUsers();
    }

    /**
     * 信息
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("info")
    public Result<?> info(@CurrentUser User user) {
        return userService.info(user);
    }

    /**
     * 资料
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    @GetMapping("profile")
    public Result<?> profile(@CurrentUser User user) {
        return userService.profile(user);
    }

    /**
     * 设置资料
     *
     * @param user         用户
     * @param setProfileCommand 设置配置文件 VO
     * @return {@link Result}<{@link ?}>
     */
    @PutMapping("profile/set")
    public Result<?> setProfile(@CurrentUser User user,
                                @RequestBody SetProfileCommand setProfileCommand) {
        return userService.setProfile(user, setProfileCommand);
    }

    @DeleteMapping("account")
    public Result<?> deleteAccount(@CurrentUser User user,
                                   HttpServletRequest request) {
        return userService.deleteAccount(user, request);
    }
}
