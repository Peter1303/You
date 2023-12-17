package top.pdev.you.domain.service.student;

import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;

import java.util.List;

/**
 * 学生服务
 * Created in 2023/12/16 9:41
 *
 * @author Peter1303
 */
public interface StudentService {
    /**
     * 获取年级
     *
     * @param student 学生
     * @return {@link Integer}
     */
    Integer getGrade(Student student);

    /**
     * 获取学院
     *
     * @param student 学生
     * @return {@link String}
     */
    String getInstitute(Student student);

    /**
     * 获取校区
     *
     * @param student 学生
     * @return {@link String}
     */
    String getCampus(Student student);

    /**
     * 获取班级
     *
     * @param student 学生
     * @return {@link String}
     */
    String getClazz(Student student);

    List<Association> getAssociations(Student student);
}
