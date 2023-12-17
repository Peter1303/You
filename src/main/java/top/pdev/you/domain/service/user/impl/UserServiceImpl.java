package top.pdev.you.domain.service.user.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.entity.role.SuperEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.enums.RedisKey;
import top.pdev.you.common.enums.Role;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.command.user.RegisterCommand;
import top.pdev.you.domain.command.user.SetProfileCommand;
import top.pdev.you.domain.command.user.UserLoginCommand;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.model.dto.AssociationBaseInfoDTO;
import top.pdev.you.domain.model.dto.WechatLoginDTO;
import top.pdev.you.domain.model.vm.LoginResultResponse;
import top.pdev.you.domain.model.vm.UserInfoResponse;
import top.pdev.you.domain.model.vm.UserProfileResponse;
import top.pdev.you.domain.service.permission.PermissionService;
import top.pdev.you.domain.service.student.StudentService;
import top.pdev.you.domain.service.teacher.TeacherService;
import top.pdev.you.domain.service.user.UserService;
import top.pdev.you.domain.service.wechat.WechatService;
import top.pdev.you.infrastructure.factory.UserFactory;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.result.ResultCode;
import top.pdev.you.infrastructure.util.TagKeyUtil;
import top.pdev.you.infrastructure.util.TokenUtil;
import top.pdev.you.persistence.mapper.AssociationMapper;
import top.pdev.you.persistence.mapper.UserMapper;
import top.pdev.you.persistence.repository.AssociationManagerRepository;
import top.pdev.you.persistence.repository.AssociationRepository;
import top.pdev.you.persistence.repository.ClassRepository;
import top.pdev.you.persistence.repository.StudentRepository;
import top.pdev.you.persistence.repository.TeacherRepository;
import top.pdev.you.persistence.repository.UserRepository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
    private StudentService studentService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private WechatService wechatService;

    @Lazy
    @Resource
    private PermissionService permissionService;

    @Resource
    private RedisService redisService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private AssociationRepository associationRepository;

    @Resource
    private AssociationManagerRepository associationManagerRepository;

    @Resource
    private ClassRepository classRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private AssociationMapper associationMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserFactory userFactory;

    @Override
    public User getUser(RoleEntity role) {
        if (role instanceof Student) {
            Student student = (Student) role;
            Long userId = student.getUserId();
            return userRepository.findById(userId);
        }
        if (role instanceof Teacher) {
            Teacher teacher = (Teacher) role;
            Long userId = teacher.getUserId();
            return userRepository.findById(userId);
        }
        return null;
    }

    @Override
    public RoleEntity getRoleDomain(User user) {
        Integer permission = user.getPermission();
        if (permission == Permission.USER.getValue()) {
            return userFactory.getStudent(user);
        } else if (permission == Permission.MANAGER.getValue()) {
            return userFactory.getManager(user);
        } else if (permission == Permission.ADMIN.getValue()) {
            return userFactory.getTeacher(user);
        }
        return new SuperEntity();
    }

    @Transactional
    @Override
    public LoginResultResponse login(UserLoginCommand vo) {
        WechatLoginDTO dto = wechatService.login(vo.getCode());
        if (Optional.ofNullable(dto).isPresent()) {
            String openId = dto.getOpenId();
            if (Optional.ofNullable(openId).isPresent()) {
                LoginResultResponse loginResultResponse = new LoginResultResponse();
                loginResultResponse.setToken(openId);
                // 系统未完成初始化
                String tag = TagKeyUtil.get(RedisKey.INIT);
                if (!redisService.hasKey(tag)) {
                    if (permissionService.hasSuperAdmin()) {
                        redisService.set(tag, true);
                    } else {
                        // 保存超管
                        User user = userFactory.newUser();
                        user.setTime(DateTime.now().toLocalDateTime());
                        user.setPermission(Permission.SUPER.getValue());
                        user.setWechatId(openId);
                        if (!userRepository.save(user)) {
                            throw new InternalErrorException("无法保存用户");
                        }
                        redisService.set(tag, true);
                        return loginResultResponse;
                    }
                }
                User user = userRepository.findByWechatId(openId);
                if (Optional.ofNullable(user).isPresent()) {
                    return loginResultResponse;
                }
                throw new BusinessException(ResultCode.NOT_REGISTERED);
            }
        }
        throw new BusinessException(ResultCode.FAILED, "登录失败");
    }

    @Transactional
    @Override
    public LoginResultResponse register(Role role, RegisterCommand vo) {
        WechatLoginDTO loginDTO = wechatService.login(vo.getCode());
        String openId = loginDTO.getOpenId();
        if (Optional.ofNullable(userRepository.findByWechatId(openId)).isPresent()) {
            throw new BusinessException(ResultCode.FAILED, "用户已注册");
        }
        User user = userFactory.newUser();
        user.setWechatId(openId);
        switch (role) {
            case STUDENT:
                Student student = userFactory.newStudent();
                Long classId = vo.getClassId();
                student.setNo(vo.getNo());
                student.setName(vo.getName());
                student.setContact(vo.getContact());
                student.setClassId(classId);
                user.setPermission(Permission.USER.getValue());
                user.setTime(LocalDateTime.now());
                if (!userRepository.save(user)) {
                    throw new InternalErrorException("无法保存用户");
                }
                student.setUserId(user.getId());
                // 检测班级是否存在
                if (!Optional.ofNullable(classRepository.findById(classId)).isPresent()) {
                    throw new BusinessException(ResultCode.FAILED, "班级不存在");
                }
                if (!Optional.ofNullable(classId).isPresent()) {
                    throw new InternalErrorException("班级 ID 空错误");
                }
                if (!studentRepository.save(student)) {
                    throw new InternalErrorException("无法保存学生");
                }
                break;
            case TEACHER:
                Teacher teacher = userFactory.newTeacher();
                teacher.setNo(vo.getNo());
                teacher.setName(vo.getName());
                teacher.setContact(vo.getContact());
                user.setPermission(Permission.ADMIN.getValue());
                user.setTime(LocalDateTime.now());
                if (!userRepository.save(user)) {
                    throw new InternalErrorException("无法保存用户");
                }
                teacher.setUserId(user.getId());
                if (!teacherRepository.save(teacher)) {
                    throw new InternalErrorException("无法保存老师");
                }
                break;
            default:
                break;
        }
        LoginResultResponse resultVO = new LoginResultResponse();
        resultVO.setToken(openId);
        return resultVO;
    }

    @Override
    public UserInfoResponse info(User user) {
        // 获取用户
        Integer permission = user.getPermission();
        String association = null;
        List<AssociationBaseInfoDTO> associations = null;
        RoleEntity role = getRoleDomain(user);
        String name = role.getName();
        String no = role.getNo();
        // 为学生
        if (role instanceof Student) {
            Student student = (Student) role;
            // 如果是负责人那么有其管理的社团
            if (role instanceof Manager) {
                List<AssociationManager> associationManagers =
                        associationManagerRepository.findByUserIdAndType(
                                user.getId(),
                                Permission.MANAGER.getValue());
                AssociationManager manager = associationManagers.get(0);
                Association one = associationRepository.findById(manager.getAssociationId());
                association = one.getName();
            }
            List<Association> list = studentService.getAssociations(student);
            associations = list
                    .stream()
                    .map(top.pdev.you.infrastructure.mapper.AssociationMapper.INSTANCE::convert)
                    .collect(Collectors.toList());
        }
        // 为老师
        if (role instanceof Teacher) {
            Teacher teacher = (Teacher) role;
            no = teacher.getNo();
            List<Association> managedList = teacherService.getManagedAssociationList(teacher);
            associations = managedList
                    .stream()
                    .map(top.pdev.you.infrastructure.mapper.AssociationMapper.INSTANCE::convert)
                    .collect(Collectors.toList());
        }
        // 信息
        UserInfoResponse vo = new UserInfoResponse();
        vo.setNo(no);
        vo.setName(name);
        vo.setPermission(permission);
        vo.setAssociation(association);
        vo.setAssociations(associations);
        return vo;
    }

    @Override
    public UserProfileResponse profile(User user) {
        // 获取用户
        Integer permission = user.getPermission();
        String clazz = null;
        String campus = null;
        String institute = null;
        Integer grade = null;
        RoleEntity role = getRoleDomain(user);
        // 为学生
        String name = role.getName();
        String no = role.getNo();
        String contact = role.getContact();
        if (role instanceof Student) {
            Student student = userFactory.getStudent(user);
            clazz = studentService.getClazz(student);
            campus = studentService.getCampus(student);
            institute = studentService.getInstitute(student);
            grade = studentService.getGrade(student);
        }
        // 为老师
        if (role instanceof Teacher) {
            Teacher teacher = userFactory.getTeacher(user);
            no = teacher.getNo();
            name = teacher.getName();
        }
        // 资料
        UserProfileResponse vo = new UserProfileResponse();
        vo.setNo(no);
        vo.setName(name);
        vo.setClazz(clazz);
        vo.setCampus(campus);
        vo.setInstitute(institute);
        vo.setContact(contact);
        vo.setGrade(grade);
        vo.setPermission(permission);
        return vo;
    }

    @Override
    public void permissionTo(User user, Permission permission) {
        user.setPermission(permission.getValue());
        if (!userRepository.updateById(user)) {
            throw new BusinessException("变更权限失败");
        }
    }

    @Transactional
    @Override
    public void setProfile(User user,
                           SetProfileCommand setProfileCommand) {
        String contact = setProfileCommand.getContact();
        RoleEntity role = getRoleDomain(user);
        if (role instanceof Student) {
            // 更改学生
            Student student = (Student) role;
            student.setContact(contact);
            if (!studentRepository.updateById(student)) {
                throw new BusinessException("无法保存联系方式");
            }
        } else if (role instanceof Teacher) {
            // 更改老师
            Teacher teacher = (Teacher) role;
            teacher.setContact(contact);
            if (!teacherRepository.updateById(teacher)) {
                throw new BusinessException("无法保存联系方式");
            }
        }
    }

    @Transactional
    @Override
    public void deleteAccount(User user,
                              HttpServletRequest request) {
        Integer permission = user.getPermission();
        if (permission == Permission.SUPER.getValue()) {
            throw new BusinessException("核心管理员不可删除");
        }
        Long id = user.getId();
        boolean deleted;
        if (permission == Permission.ADMIN.getValue()) {
            deleted = teacherRepository.deleteByUserId(id);
        } else {
            deleted = studentRepository.deleteByUserId(id);
        }
        if (deleted) {
            deleted = userRepository.deleteById(id);
        }
        if (!deleted) {
            throw new BusinessException("删除失败");
        }
        String token = TokenUtil.getTokenByHeader(request);
        redisService.delete(TagKeyUtil.get(RedisKey.LOGIN_TOKEN, token));
    }

    @Override
    public List<UserInfoResponse> getUsers() {
        List<User> list = userMapper.selectList(new LambdaQueryWrapper<>());
        List<UserInfoResponse> userInfoList = new ArrayList<>();
        list.forEach(user -> {
            // 如果是超级管理那么跳过
            if (Permission.SUPER.getValue() == user.getPermission()) {
                return;
            }
            RoleEntity role = getRoleDomain(user);
            Integer permission = user.getPermission();
            String contact;
            String clazz = null;
            String no = role.getNo();
            String name = role.getName();
            List<AssociationBaseInfoDTO> associations;
            if (role instanceof Teacher) {
                Teacher teacher = (Teacher) role;
                contact = teacher.getContact();
                associations = associationMapper.getListByAdmin(user.getId());
            } else {
                Student student = (Student) role;
                contact = student.getContact();
                clazz = studentService.getClazz(student);
                // 如果是负责人那么只需要其管理的社团
                if (permission == Permission.MANAGER.getValue()) {
                    associations = associationMapper.getListByAdmin(user.getId());
                } else {
                    associations = associationMapper.getListByParticipant(student.getId());
                }
            }
            UserInfoResponse infoVO = new UserInfoResponse();
            infoVO.setId(user.getId());
            infoVO.setNo(no);
            infoVO.setPermission(permission);
            infoVO.setName(name);
            infoVO.setContact(contact);
            infoVO.setAssociations(associations);
            infoVO.setClazz(clazz);
            userInfoList.add(infoVO);
        });
        return userInfoList;
    }
}
