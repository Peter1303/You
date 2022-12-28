package top.pdev.you.domain.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import top.pdev.you.application.service.WechatService;
import top.pdev.you.common.constant.RedisKey;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.enums.Role;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.entity.data.TeacherDO;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.mapper.AssociationMapper;
import top.pdev.you.domain.mapper.UserMapper;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.AdminService;
import top.pdev.you.domain.service.UserService;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.infrastructure.result.ResultCode;
import top.pdev.you.infrastructure.util.TokenUtil;
import top.pdev.you.interfaces.assembler.AssociationAssembler;
import top.pdev.you.interfaces.model.dto.AssociationBaseInfoDTO;
import top.pdev.you.interfaces.model.dto.WechatLoginDTO;
import top.pdev.you.interfaces.model.vo.LoginResultVO;
import top.pdev.you.interfaces.model.vo.UserInfoVO;
import top.pdev.you.interfaces.model.vo.UserProfileVO;
import top.pdev.you.interfaces.model.vo.req.RegisterVO;
import top.pdev.you.interfaces.model.vo.req.SetProfileVO;
import top.pdev.you.interfaces.model.vo.req.UserLoginVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private AssociationRepository associationRepository;

    @Resource
    private UserFactory userFactory;

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
                User user = userRepository.findByToken(openId);
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
        if (Optional.ofNullable(userRepository.findByToken(openId)).isPresent()) {
            throw new BusinessException(ResultCode.FAILED, "用户已注册");
        }
        User user = userFactory.newUser();
        user.setOpenId(openId);
        switch (role) {
            case STUDENT:
                Student student = userFactory.newStudent();
                StudentDO studentDO = new StudentDO();
                studentDO.setNo(vo.getNo());
                studentDO.setName(vo.getName());
                studentDO.setContact(vo.getContact());
                studentDO.setClassId(vo.getClassId());
                student.save(studentDO);
                user.save(student);
                break;
            case TEACHER:
                Teacher teacher = userFactory.newTeacher();
                TeacherDO teacherDO = new TeacherDO();
                teacherDO.setNo(vo.getNo());
                teacherDO.setName(vo.getName());
                teacherDO.setContact(vo.getContact());
                teacher.save(teacherDO);
                user.save(teacher);
                break;
            default:
                break;
        }
        LoginResultVO resultVO = new LoginResultVO();
        resultVO.setToken(openId);
        return Result.ok().setData(resultVO);
    }

    @Override
    public Result<?> info(TokenInfo tokenInfo) {
        // 获取用户
        User user = userRepository.find(new UserId(tokenInfo.getUid()));
        Integer permission = user.getPermission();
        String no = null;
        String name = null;
        String association = null;
        List<AssociationBaseInfoDTO> associations = null;
        // 为学生
        if (Permission.USER.getValue() == permission
                || Permission.MANAGER.getValue() == permission) {
            Student student = userFactory.getStudent(user);
            no = student.getNo();
            name = student.getName();
            // 如果是负责人那么有其管理的社团
            if (Permission.MANAGER.getValue() == permission) {
                Association one = associationRepository.getOne(student);
                association = one.getName();
            }
            List<AssociationDO> list = student.getAssociations();
            associations = list
                    .stream()
                    .map(AssociationAssembler.INSTANCE::convert)
                    .collect(Collectors.toList());
        }
        // 为老师
        if (Permission.ADMIN.getValue() == permission) {
            Teacher teacher = userFactory.getTeacher(user);
            no = teacher.getNo();
            name = teacher.getName();
            List<AssociationDO> managedList = teacher.getManagedAssociationList();
            associations = managedList
                    .stream()
                    .map(AssociationAssembler.INSTANCE::convert)
                    .collect(Collectors.toList());
        }
        // 为超管
        if (Permission.SUPER.getValue() == permission) {
            name = "超级管理员";
        }
        // 信息
        UserInfoVO vo = new UserInfoVO();
        vo.setNo(no);
        vo.setName(name);
        vo.setPermission(permission);
        vo.setAssociation(association);
        vo.setAssociations(associations);
        return Result.ok(vo);
    }

    @Override
    public Result<?> profile(TokenInfo tokenInfo) {
        // 获取用户
        User user = userRepository.find(new UserId(tokenInfo.getUid()));
        Integer permission = user.getPermission();
        String no = null;
        String name = null;
        String clazz = null;
        String campus = null;
        String institute = null;
        String contact = null;
        Integer grade = null;
        // 为学生
        if (Permission.USER.getValue() == permission
                || Permission.MANAGER.getValue() == permission) {
            Student student = userFactory.getStudent(user);
            no = student.getNo();
            name = student.getName();
            contact = student.getContact();
            clazz = student.getClazz();
            campus = student.getCampus();
            institute = student.getInstitute();
            grade = student.getGrade();
        }
        // 为老师
        if (Permission.ADMIN.getValue() == permission) {
            Teacher teacher = userFactory.getTeacher(user);
            no = teacher.getNo();
            name = teacher.getName();
            contact = teacher.getContact();
        }
        // 为超管
        if (Permission.SUPER.getValue() == permission) {
            name = "超级管理员";
        }
        // 资料
        UserProfileVO vo = new UserProfileVO();
        vo.setNo(no);
        vo.setName(name);
        vo.setClazz(clazz);
        vo.setCampus(campus);
        vo.setInstitute(institute);
        vo.setContact(contact);
        vo.setGrade(grade);
        vo.setPermission(permission);
        return Result.ok(vo);
    }

    @Override
    public Result<?> setProfile(TokenInfo tokenInfo,
                                SetProfileVO setProfileVO) {
        User user = userRepository.find(new UserId(tokenInfo.getUid()));
        Integer permission = user.getPermission();
        String contact = setProfileVO.getContact();
        if (permission == Permission.USER.getValue()) {
            // 更改学生
            Student student = userFactory.getStudent(user);
            student.saveContact(contact);
        } else if (permission == Permission.MANAGER.getValue()) {
            // 更改老师
            Teacher teacher = userFactory.getTeacher(user);
            teacher.saveContact(contact);
        }
        return Result.ok();
    }

    @Override
    public Result<?> deleteAccount(TokenInfo tokenInfo,
                                   HttpServletRequest request) {
        User user = userRepository.find(new UserId(tokenInfo.getUid()));
        user.delete();
        String token = TokenUtil.getTokenByHeader(request);
        redisService.delete(RedisKey.loginToken(token));
        return Result.ok();
    }

    @Override
    public Result<?> getUsers() {
        // 时间不足 暂时用 MVC 后续可改为注入
        AssociationMapper associationMapper = SpringUtil.getBean(AssociationMapper.class);
        UserMapper userMapper = SpringUtil.getBean(UserMapper.class);
        List<UserDO> list = userMapper.selectList(new LambdaQueryWrapper<>());
        List<UserInfoVO> userInfoList = new ArrayList<>();
        list.forEach(userDO -> {
            // 如果是超级管理那么跳过
            if (!Optional.ofNullable(userDO.getTargetId()).isPresent()) {
                return;
            }
            User user = userFactory.getUser(userDO);
            Integer permission = user.getPermission();
            String no;
            String name;
            String contact;
            String clazz = null;
            List<AssociationBaseInfoDTO> associations;
            if (permission == Permission.ADMIN.getValue()) {
                Teacher teacher = userFactory.getTeacher(user);
                no = teacher.getNo();
                name = teacher.getName();
                contact = teacher.getContact();
                associations = associationMapper.getListByAdmin(userDO.getId());
            } else {
                Student student = userFactory.getStudent(user);
                no = student.getNo();
                name = student.getName();
                contact = student.getContact();
                clazz = student.getClazz();
                // 如果是负责人那么只需要其管理的社团
                if (permission == Permission.MANAGER.getValue()) {
                    associations = associationMapper.getListByAdmin(userDO.getId());
                } else {
                    associations = associationMapper.getListByParticipant(
                            student.getStudentId().getId());
                }
            }
            UserInfoVO infoVO = new UserInfoVO();
            infoVO.setId(userDO.getId());
            infoVO.setNo(no);
            infoVO.setPermission(permission);
            infoVO.setName(name);
            infoVO.setContact(contact);
            infoVO.setAssociations(associations);
            infoVO.setClazz(clazz);
            userInfoList.add(infoVO);
        });
        return Result.ok().setData(userInfoList);
    }
}
