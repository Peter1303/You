package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.factory.CampusFactory;
import top.pdev.you.domain.factory.ClassFactory;
import top.pdev.you.domain.factory.InstituteFactory;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.domain.repository.StudentRepository;
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
public class Student extends BaseEntity {
    private User user;
    private StudentId studentId;
    private String no;
    private String name;
    private String contact;
    private String clazz;
    private String campus;

    /**
     * 记录
     */
    private StudentDO studentDO;

    private final StudentRepository studentRepository = SpringUtil.getBean(StudentRepository.class);
    private final ClassRepository classRepository = SpringUtil.getBean(ClassRepository.class);
    private final AssociationRepository associationRepository = SpringUtil.getBean(AssociationRepository.class);
    private final ClassFactory classFactory = SpringUtil.getBean(ClassFactory.class);
    private final AssociationFactory associationFactory = SpringUtil.getBean(AssociationFactory.class);
    private final CampusFactory campusFactory = SpringUtil.getBean(CampusFactory.class);
    private final InstituteFactory instituteFactory = SpringUtil.getBean(InstituteFactory.class);

    public Student(User user) {
        if (!Optional.ofNullable(user).isPresent()) {
            return;
        }
        this.user = user;
        this.studentId = new StudentId(user.getTargetId());
        this.studentDO = studentRepository.getDO(studentId);
        Optional.ofNullable(studentDO)
                .orElseThrow(() -> new BusinessException("没有找到学生"));
        this.name = studentDO.getName();
        this.no = studentDO.getNo();
        this.contact = studentDO.getContact();
    }

    public List<AssociationDO> getAssociations() {
        return associationRepository.ofStudentList(this);
    }

    /**
     * 获取班级
     *
     * @return {@link String}
     */
    public String getClazz() {
        super.checkStudent(this);
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
        super.checkStudent(this);
        Campus c = campusFactory.newCampus();
        return c.getStudentCampusName(this);
    }

    /**
     * 获取学院
     *
     * @return {@link String}
     */
    public String getInstitute() {
        super.checkStudent(this);
        Institute institute = instituteFactory.newInstitute();
        return institute.getStudentInstituteName(this);
    }

    /**
     * 获取年级
     *
     * @return {@link Integer}
     */
    public Integer getGrade() {
        super.checkStudent(this);
        Long classId = getStudentDO().getClassId();
        Clazz clz = classRepository.find(classId);
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
        if (!studentRepository.setContact(studentId, contact)) {
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
        if (!Optional.ofNullable(classRepository.find(classId)).isPresent()) {
            throw new BusinessException(ResultCode.FAILED, "班级不存在");
        }
        if (!Optional.ofNullable(classId).isPresent()) {
            throw new InternalErrorException("班级 ID 空错误");
        }
        if (!studentRepository.save(studentDO)) {
            throw new InternalErrorException("无法保存学生");
        }
    }
}
