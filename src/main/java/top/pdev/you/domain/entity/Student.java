package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.factory.CampusFactory;
import top.pdev.you.domain.factory.ClassFactory;
import top.pdev.you.domain.factory.InstituteFactory;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.infrastructure.result.ResultCode;

import java.util.List;
import java.util.Optional;

/**
 * 学生领域
 * Created in 2022/10/3 18:11
 *
 * @author Peter1303
 */
@Data
public class Student extends RoleEntity {
    private Long id;
    private Long classId;
    private Long userId;
    private String no;
    private String name;
    private String contact;
    private String clazz;
    private String campus;

    private User user;

    /**
     * 初始化
     *
     * @param user 用户
     */
    private void init(User user) {
        if (!Optional.ofNullable(user).isPresent()) {
            return;
        }
        Long userId = user.getId();
        StudentRepository studentRepository =
                SpringUtil.getBean(StudentRepository.class);
        Student student = studentRepository.findByUserId(userId);
        Optional.ofNullable(student)
                .orElseThrow(() -> new BusinessException("没有找到学生"));
        this.id = student.getId();
        this.classId = student.getClassId();
        this.name = student.getName();
        this.no = student.getNo();
        this.contact = student.getContact();
    }

    public Student(User user) {
        init(user);
    }

    public Student(StudentDO studentDO) {
        this.id = studentDO.getId();
        this.classId = studentDO.getClassId();
        this.name = studentDO.getName();
        this.no = studentDO.getNo();
        this.contact = studentDO.getContact();
        this.userId = studentDO.getUserId();
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

    public List<AssociationDO> getAssociations() {
        AssociationRepository associationRepository =
                SpringUtil.getBean(AssociationRepository.class);
        return associationRepository.ofStudentList(this);
    }

    /**
     * 获取班级
     *
     * @return {@link String}
     */
    public String getClazz() {
        ClassFactory classFactory =
                SpringUtil.getBean(ClassFactory.class);
        Clazz cls = classFactory.newClazz();
        this.clazz = cls.getStudentClassName(this);
        return this.clazz;
    }

    /**
     * 获取校区
     *
     * @return {@link String}
     */
    public String getCampus() {
        CampusFactory campusFactory =
                SpringUtil.getBean(CampusFactory.class);
        Campus c = campusFactory.newCampus();
        return c.getStudentCampusName(this);
    }

    /**
     * 获取学院
     *
     * @return {@link String}
     */
    public String getInstitute() {
        InstituteFactory instituteFactory =
                SpringUtil.getBean(InstituteFactory.class);
        Institute institute = instituteFactory.newInstitute();
        return institute.getStudentInstituteName(this);
    }

    /**
     * 获取年级
     *
     * @return {@link Integer}
     */
    public Integer getGrade() {
        Long classId = getClassId();
        ClassRepository classRepository =
                SpringUtil.getBean(ClassRepository.class);
        Clazz clz = classRepository.findById(classId);
        if (Optional.ofNullable(clz).isPresent()) {
            return clz.getGrade();
        }
        return null;
    }

    /**
     * 保存联系方式
     *
     * @param contact 联系
     */
    public void saveContact(String contact) {
        this.contact = contact;
        StudentDO studentDO = new StudentDO();
        studentDO.setId(this.id);
        studentDO.setContact(contact);
        StudentRepository studentRepository =
                SpringUtil.getBean(StudentRepository.class);
        if (!studentRepository.updateById(studentDO)) {
            throw new BusinessException("无法保存联系方式");
        }
    }

    /**
     * 保存
     *
     * @param studentDO 学生 DO
     */
    public void save(StudentDO studentDO) {
        // 检测班级是否存在
        Long classId = studentDO.getClassId();
        ClassRepository classRepository =
                SpringUtil.getBean(ClassRepository.class);
        if (!Optional.ofNullable(classRepository.findById(classId)).isPresent()) {
            throw new BusinessException(ResultCode.FAILED, "班级不存在");
        }
        if (!Optional.ofNullable(classId).isPresent()) {
            throw new InternalErrorException("班级 ID 空错误");
        }
        StudentRepository studentRepository =
                SpringUtil.getBean(StudentRepository.class);
        if (!studentRepository.save(studentDO)) {
            throw new InternalErrorException("无法保存学生");
        }
        this.id = studentDO.getId();
    }
}
