package top.pdev.you.domain.service.impl;

import cn.hutool.core.date.DateTime;
import org.springframework.stereotype.Service;
import top.pdev.you.application.service.WechatService;
import top.pdev.you.common.constant.RedisKey;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.enums.Role;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.entity.data.TeacherDO;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.factory.StudentFactory;
import top.pdev.you.domain.factory.TeacherFactory;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.AdminService;
import top.pdev.you.domain.service.UserService;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.infrastructure.result.ResultCode;
import top.pdev.you.interfaces.model.dto.WechatLoginDTO;
import top.pdev.you.interfaces.model.vo.LoginResultVO;
import top.pdev.you.interfaces.model.vo.req.RegisterVO;
import top.pdev.you.interfaces.model.vo.req.UserLoginVO;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户服务实现类
 * Created in 2022/10/2 17:56
 *
 * @author Peter1303
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private WechatService wechatService;

    @Resource
    private AdminService adminService;

    @Resource
    private RedisService redisService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserFactory userFactory;

    @Resource
    private TeacherFactory teacherFactory;

    @Resource
    private StudentFactory studentFactory;

    @Override
    public Result<?> login(UserLoginVO vo) {
        WechatLoginDTO dto = wechatService.login(vo.getCode());
        if (Optional.ofNullable(dto).isPresent()) {
            String openId = dto.getOpenId();
            if (Optional.ofNullable(openId).isPresent()) {
                LoginResultVO loginResultVO = new LoginResultVO();
                loginResultVO.setToken(openId);
                // 系统未完成初始化
                if (!redisService.hasKey(RedisKey.init())) {
                    if (adminService.hasSuperAdmin()) {
                        redisService.set(RedisKey.init(), true);
                    } else {
                        // 保存超管
                        User user = userFactory.newUser();
                        UserDO userDO = new UserDO();
                        userDO.setTime(DateTime.now().toLocalDateTime());
                        userDO.setPermission(Permission.SUPER.getValue());
                        userDO.setWechatId(openId);
                        user.save(userDO);
                        redisService.set(RedisKey.init(), true);
                        return Result.ok()
                                .setData(loginResultVO)
                                .setMessage("超级管理员");
                    }
                }
                User user = userRepository.findByOpenId(openId);
                if (Optional.ofNullable(user).isPresent()) {
                    return Result.ok().setData(loginResultVO);
                }
                throw new BusinessException(ResultCode.NOT_REGISTERED);
            }
        }
        throw new BusinessException(ResultCode.FAILED, "登录失败");
    }

    @Override
    public Result<?> register(Role role, RegisterVO vo) {
        WechatLoginDTO loginDTO = wechatService.login(vo.getCode());
        String openId = loginDTO.getOpenId();
        if (Optional.ofNullable(userRepository.findByOpenId(openId)).isPresent()) {
            throw new BusinessException(ResultCode.FAILED, "用户已注册");
        }
        switch (role) {
            case STUDENT:
                Student student = studentFactory.newStudent();
                StudentDO studentDO = new StudentDO();
                studentDO.setNo(vo.getNo());
                studentDO.setName(vo.getName());
                studentDO.setContact(vo.getContact());
                studentDO.setClassId(vo.getClassId());
                student.save(studentDO);
                break;
            case TEACHER:
                Teacher teacher = teacherFactory.newTeacher();
                TeacherDO teacherDO = new TeacherDO();
                teacherDO.setNo(vo.getNo());
                teacherDO.setName(vo.getName());
                teacherDO.setContact(vo.getContact());
                teacher.save(teacherDO);
                User user = userFactory.newUser();
                user.setOpenId(openId);
                user.save(teacher);
                break;
            default:
                break;
        }
        LoginResultVO resultVO = new LoginResultVO();
        resultVO.setToken(openId);
        return Result.ok().setData(resultVO);
    }
}
