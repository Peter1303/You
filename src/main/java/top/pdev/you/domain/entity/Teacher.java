package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.TeacherDO;
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
@Data
public class Teacher extends RoleEntity {
    private Long id;
    private Long userId;
    private String name;
    private String no;
    private String contact;

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

    public Teacher(User user) {
        init(user);
    }

    public Teacher(TeacherDO teacherDO) {
        this.id = teacherDO.getId();
        this.name = teacherDO.getName();
        this.no = teacherDO.getNo();
        this.contact = teacherDO.getContact();
    }

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
     * @return {@link List}<{@link AssociationDO}>
     */
    public List<AssociationDO> getManagedAssociationList() {
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
        TeacherDO teacherDO = new TeacherDO();
        teacherDO.setId(this.id);
        teacherDO.setContact(contact);
        TeacherRepository teacherRepository =
                SpringUtil.getBean(TeacherRepository.class);
        if (!teacherRepository.updateById(teacherDO)) {
            throw new BusinessException("无法保存联系方式");
        }
    }

    /**
     * 保存
     *
     * @param teacherDO 老师 DO
     */
    public void save(TeacherDO teacherDO) {
        TeacherRepository teacherRepository =
                SpringUtil.getBean(TeacherRepository.class);
        if (!teacherRepository.save(teacherDO)) {
            throw new InternalErrorException("无法保存老师");
        }
        this.id = teacherDO.getId();
    }
}
