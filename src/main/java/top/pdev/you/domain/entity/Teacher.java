package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.TeacherRepository;
import top.pdev.you.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * 老师领域
 * Created in 2022/10/3 16:24
 *
 * @author Peter1303
 */
@TableName("teacher")
@Data
public class Teacher extends RoleEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 名字
     */
    private String name;

    /**
     * 工号
     */
    private String no;

    /**
     * 联系
     */
    private String contact;


    @TableField(exist = false)
    private User user;

    private void init(User user) {
        if (!Optional.ofNullable(user).isPresent()) {
            return;
        }
        this.userId = user.getId();
        TeacherRepository teacherRepository =
                SpringUtil.getBean(TeacherRepository.class);
        Teacher teacher = teacherRepository.findByUserId(userId);
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.no = teacher.getNo();
        this.contact = teacher.getContact();
    }

    private Teacher() {
    }

    public Teacher(User user) {
        init(user);
    }

    @JsonIgnore
    @Override
    public User getUser() {
        if (!Optional.ofNullable(user).isPresent()) {
            UserRepository userRepository =
                    SpringUtil.getBean(UserRepository.class);
            user = userRepository.findById(userId);
        }
        return user;
    }

    /**
     * 获取管理社团列表
     *
     * @return {@link List}<{@link Association}>
     */
    @JsonIgnore
    public List<Association> getManagedAssociationList() {
        AssociationRepository associationRepository =
                SpringUtil.getBean(AssociationRepository.class);
        return associationRepository.getManagedList(this);
    }

    /**
     * 保存联系方式
     *
     * @param contact 联系
     */
    public void saveContact(String contact) {
        this.contact = contact;
        Teacher teacher = new Teacher();
        teacher.setId(this.id);
        teacher.setContact(contact);
        TeacherRepository teacherRepository =
                SpringUtil.getBean(TeacherRepository.class);
        if (!teacherRepository.updateById(teacher)) {
            throw new BusinessException("无法保存联系方式");
        }
    }

    /**
     * 保存
     */
    public void save() {
        TeacherRepository teacherRepository =
                SpringUtil.getBean(TeacherRepository.class);
        if (!teacherRepository.save(this)) {
            throw new InternalErrorException("无法保存老师");
        }
    }
}
