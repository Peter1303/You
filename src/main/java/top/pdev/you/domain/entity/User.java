package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.entity.role.SuperEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.domain.repository.TeacherRepository;
import top.pdev.you.domain.repository.UserRepository;

import java.time.LocalDateTime;

/**
 * 用户领域
 * Created in 2022/9/30 22:32
 *
 * @author Peter1303
 */
@TableName("user")
@Data
public class User extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信 ID
     */
    private String wechatId;

    /**
     * 权限
     */
    private Integer permission;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 保存
     */
    public void save() {
        UserRepository userRepository =
                SpringUtil.getBean(UserRepository.class);
        if (!userRepository.save(this)) {
            throw new InternalErrorException("无法保存用户");
        }
    }

    /**
     * 保存
     *
     * @param role 角色
     */
    public void save(RoleEntity role) {
        if (role instanceof Student) {
            setPermission(Permission.USER.getValue());
        } else if (role instanceof Teacher) {
            setPermission(Permission.ADMIN.getValue());
        }
        save();
    }

    /**
     * 删除
     */
    public void delete() {
        if (permission == Permission.SUPER.getValue()) {
            throw new BusinessException("核心管理员不可删除");
        }
        if (permission == Permission.ADMIN.getValue()) {
            TeacherRepository teacherRepository = SpringUtil.getBean(TeacherRepository.class);
            teacherRepository.removeById(id);
        } else {
            StudentRepository studentRepository =
                    SpringUtil.getBean(StudentRepository.class);
            studentRepository.removeById(id);
        }
        UserRepository userRepository =
                SpringUtil.getBean(UserRepository.class);
        userRepository.removeById(id);
    }

    /**
     * 权限到
     *
     * @param permission 权限
     */
    public void permissionTo(Permission permission) {
        User user = new User();
        user.setId(getId());
        user.setPermission(permission.getValue());
        UserRepository userRepository =
                SpringUtil.getBean(UserRepository.class);
        if (!userRepository.updateById(user)) {
            throw new BusinessException("变更权限失败");
        }
        setPermission(permission.getValue());
    }

    /**
     * 获取角色域
     *
     * @return {@link RoleEntity}
     */
    @JsonIgnore
    public RoleEntity getRoleDomain() {
        UserFactory userFactory =
                SpringUtil.getBean(UserFactory.class);
        if (permission == Permission.USER.getValue()) {
            return userFactory.getStudent(this);
        } else if (permission == Permission.MANAGER.getValue()) {
            return new Manager(this);
        } else if (permission == Permission.ADMIN.getValue()) {
            return userFactory.getTeacher(this);
        }
        return new SuperEntity();
    }
}
