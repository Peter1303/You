package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.TeacherDO;
import top.pdev.you.domain.entity.types.TeacherId;
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
public class Teacher extends BaseEntity {
    private User user;
    private TeacherId teacherId;
    private String name;
    private String no;
    private String contact;

    private TeacherDO teacherDO;

    private final TeacherRepository teacherRepository = SpringUtil.getBean(TeacherRepository.class);
    private final UserRepository userRepository = SpringUtil.getBean(UserRepository.class);
    private final AssociationRepository associationRepository = SpringUtil.getBean(AssociationRepository.class);

    private void init(User user) {
        if (!Optional.ofNullable(user).isPresent()) {
            return;
        }
        this.user = user;
        this.teacherId = new TeacherId(user.getTargetId());
        teacherDO = teacherRepository.getDO(teacherId);
        Optional.ofNullable(teacherDO)
                .orElseThrow(() -> new BusinessException("没有找到老师"));
        this.name = teacherDO.getName();
        this.no = teacherDO.getNo();
        this.contact = teacherDO.getContact();
    }

    public Teacher(User user) {
        init(user);
    }

    public Teacher(TeacherId teacherId) {
        init(userRepository.find(teacherId));
    }

    /**
     * 获取管理社团列表
     *
     * @return {@link List}<{@link AssociationDO}>
     */
    public List<AssociationDO> getManagedAssociationList() {
        return associationRepository.getManagedList(this);
    }

    /**
     * 保存联系方式
     *
     * @param contact 联系
     */
    public void saveContact(String contact) {
        if (!teacherRepository.setContact(teacherId, contact)) {
            throw new BusinessException("无法保存联系方式");
        }
    }

    /**
     * 保存
     *
     * @param teacherDO 老师 DO
     */
    public void save(TeacherDO teacherDO) {
        if (!teacherRepository.save(teacherDO)) {
            throw new InternalErrorException("无法保存老师");
        }
        this.teacherId = new TeacherId(teacherDO.getId());
    }
}
