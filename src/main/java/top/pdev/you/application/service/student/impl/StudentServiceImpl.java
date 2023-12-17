package top.pdev.you.application.service.student.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.application.service.student.StudentService;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.persistence.repository.AssociationRepository;
import top.pdev.you.persistence.repository.CampusRepository;
import top.pdev.you.persistence.repository.ClassRepository;
import top.pdev.you.persistence.repository.InstituteRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学生服务实现类
 * Created in 2023/12/16 9:42
 *
 * @author Peter1303
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private ClassRepository classRepository;

    @Resource
    private InstituteRepository instituteRepository;

    @Resource
    private CampusRepository campusRepository;

    @Resource
    private AssociationRepository associationRepository;

    @Override
    public Integer getGrade(Student student) {
        Long classId = student.getClassId();
        Clazz clz = classRepository.findById(classId);
        return clz.getYear();
    }

    @Override
    public String getInstitute(Student student) {
        Long classId = student.getClassId();
        Clazz clazz = classRepository.findById(classId);
        Institute institute = instituteRepository.findById(clazz.getInstituteId());
        return institute.getName();
    }

    @Override
    public String getCampus(Student student) {
        Long classId = student.getClassId();
        Clazz clazz = classRepository.findById(classId);
        Campus campus = campusRepository.findById(clazz.getCampusId());
        return campus.getName();
    }

    @Override
    public String getClazz(Student student) {
        Long classId = student.getClassId();
        Clazz cls = classRepository.findById(classId);
        return cls.getName();
    }

    @Override
    public List<Association> getAssociations(Student student) {
        return associationRepository.ofStudentList(student);
    }
}
