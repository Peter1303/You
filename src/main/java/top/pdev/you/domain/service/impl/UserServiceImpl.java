package top.pdev.you.domain.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import top.pdev.you.application.service.WechatService;
import top.pdev.you.common.constant.RedisKey;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.enums.Role;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.mapper.AssociationMapper;
import top.pdev.you.domain.mapper.UserMapper;
import top.pdev.you.domain.repository.AssociationManagerRepository;
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
    private AssociationManagerRepository associationManagerRepository;

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
                        user.setTime(DateTime.now().toLocalDateTime());
                        user.setPermission(Permission.SUPER.getValue());
                        user.setWechatId(openId);
                        user.save();
                        redisService.set(RedisKey.init(), true);
                        return Result.ok()
                                .setData(loginResultVO)
                                .setMessage("超级管理员");
                    }
                }
                User user = userRepository.findByWechatId(openId);
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
        if (Optional.ofNullable(userRepository.findByWechatId(openId)).isPresent()) {
            throw new BusinessException(ResultCode.FAILED, "用户已注册");
        }
        User user = userFactory.newUser();
        user.setWechatId(openId);
        switch (role) {
            case STUDENT:
                Student student = userFactory.newStudent();
                student.setNo(vo.getNo());
                student.setName(vo.getName());
                student.setContact(vo.getContact());
                student.setClassId(vo.getClassId());
                user.save(student);
                student.setUserId(user.getId());
                student.save();
                break;
            case TEACHER:
                Teacher teacher = userFactory.newTeacher();
                teacher.setNo(vo.getNo());
                teacher.setName(vo.getName());
                teacher.setContact(vo.getContact());
                user.save(teacher);
                teacher.setUserId(user.getId());
                teacher.save();
                break;
            default:
                break;
        }
        LoginResultVO resultVO = new LoginResultVO();
        resultVO.setToken(openId);
        return Result.ok().setData(resultVO);
    }

    @Override
    public Result<?> info(User user) {
        // 获取用户
        Integer permission = user.getPermission();
        String association = null;
        List<AssociationBaseInfoDTO> associations = null;
        RoleEntity role = user.getRoleDomain();
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
            List<Association> list = student.getAssociations();
            associations = list
                    .stream()
                    .map(AssociationAssembler.INSTANCE::convert)
                    .collect(Collectors.toList());
        }
        // 为老师
        if (role instanceof Teacher) {
            Teacher teacher = (Teacher) role;
            no = teacher.getNo();
            List<Association> managedList = teacher.getManagedAssociationList();
            associations = managedList
                    .stream()
                    .map(AssociationAssembler.INSTANCE::convert)
                    .collect(Collectors.toList());
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
    public Result<?> profile(User user) {
        // 获取用户
        Integer permission = user.getPermission();
        String clazz = null;
        String campus = null;
        String institute = null;
        Integer grade = null;
        RoleEntity role = user.getRoleDomain();
        // 为学生
        String name = role.getName();
        String no = role.getNo();
        String contact = role.getContact();
        if (role instanceof Student) {
            Student student = userFactory.getStudent(user);
            clazz = student.getClazz();
            campus = student.getCampus();
            institute = student.getInstitute();
            grade = student.getGrade();
        }
        // 为老师
        if (role instanceof Teacher) {
            Teacher teacher = userFactory.getTeacher(user);
            no = teacher.getNo();
            name = teacher.getName();
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
    public Result<?> setProfile(User user,
                                SetProfileVO setProfileVO) {
        String contact = setProfileVO.getContact();
        RoleEntity role = user.getRoleDomain();
        if (role instanceof Student) {
            // 更改学生
            Student student = (Student) role;
            student.saveContact(contact);
        } else if (role instanceof Teacher) {
            // 更改老师
            Teacher teacher = (Teacher) role;
            teacher.saveContact(contact);
        }
        return Result.ok();
    }

    @Override
    public Result<?> deleteAccount(User user,
                                   HttpServletRequest request) {
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
        List<User> list = userMapper.selectList(new LambdaQueryWrapper<>());
        List<UserInfoVO> userInfoList = new ArrayList<>();
        list.forEach(user -> {
            // 如果是超级管理那么跳过
            if (Permission.SUPER.getValue() == user.getPermission()) {
                return;
            }
            RoleEntity role = user.getRoleDomain();
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
                clazz = student.getClazz();
                // 如果是负责人那么只需要其管理的社团
                if (permission == Permission.MANAGER.getValue()) {
                    associations = associationMapper.getListByAdmin(user.getId());
                } else {
                    associations = associationMapper.getListByParticipant(student.getId());
                }
            }
            UserInfoVO infoVO = new UserInfoVO();
            infoVO.setId(user.getId());
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
