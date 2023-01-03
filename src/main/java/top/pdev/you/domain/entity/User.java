package top.pdev.you.domain.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import top.pdev.you.common.entity.role.ManagerEntity;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.entity.role.SuperEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.domain.repository.TeacherRepository;
import top.pdev.you.domain.repository.UserRepository;

import java.util.Optional;

/**
 * 用户领域
 * Created in 2022/9/30 22:32
 *
 * @author Peter1303
 */
@Data
public class User extends BaseEntity {
    private Long id;
    private String openId;
    private Integer permission;

    @Getter(AccessLevel.NONE)
    private final UserRepository userRepository = SpringUtil.getBean(UserRepository.class);

    @Getter(AccessLevel.NONE)
    private final StudentRepository studentRepository = SpringUtil.getBean(StudentRepository.class);

    @Getter(AccessLevel.NONE)
    private final TeacherRepository teacherRepository = SpringUtil.getBean(TeacherRepository.class);

    @Getter(AccessLevel.NONE)
    private final UserFactory userFactory = SpringUtil.getBean(UserFactory.class);

    public User(UserDO userDO) {
        if (!Optional.ofNullable(userDO).isPresent()) {
            return;
        }
        this.id =userDO.getId();
        this.openId = userDO.getWechatId();
        this.permission = userDO.getPermission();
    }

    /**
     * 保存
     *
     * @param userDO 用户 DO
     */
    public void save(UserDO userDO) {
        if (!userRepository.save(userDO)) {
            throw new InternalErrorException("无法保存用户");
        }
    }

    /**
     * 保存
     *
     * @param role 角色
     */
    public void save(RoleEntity role) {
        UserDO userDO = new UserDO();
        userDO.setWechatId(openId);
        userDO.setTime(DateTime.now().toLocalDateTime());
        if (role instanceof Student) {
            userDO.setPermission(Permission.USER.getValue());
        } else if (role instanceof Teacher) {
            userDO.setPermission(Permission.ADMIN.getValue());
        }
        save(userDO);
    }

    /**
     * 删除
     */
    public void delete() {
        if (permission == Permission.SUPER.getValue()) {
            throw new BusinessException("核心管理员不可删除");
        }
        if (permission == Permission.ADMIN.getValue()) {
            teacherRepository.removeById(id);
        } else {
            studentRepository.removeById(id);
        }
        userRepository.removeById(id);
    }

    /**
     * 权限到
     *
     * @param permission 权限
     */
    public void permissionTo(Permission permission) {
        UserDO userDO = new UserDO();
        userDO.setId(getId());
        userDO.setPermission(permission.getValue());
        if (!userRepository.updateById(userDO)) {
            throw new BusinessException("变更权限失败");
        }
        setPermission(permission.getValue());
    }

    /**
     * 获取角色域
     *
     * @return {@link RoleEntity}
     */
    public RoleEntity getRoleDomain() {
        if (permission == Permission.USER.getValue()) {
            return userFactory.getStudent(this);
        } else if (permission == Permission.MANAGER.getValue()) {
            return new ManagerEntity(this);
        } else if (permission == Permission.ADMIN.getValue()) {
            return userFactory.getTeacher(this);
        }
        return new SuperEntity();
    }
}
