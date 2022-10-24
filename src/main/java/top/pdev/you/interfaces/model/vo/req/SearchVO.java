package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.pdev.you.common.validator.intefaces.Clazz;
import top.pdev.you.common.validator.intefaces.Student;
import top.pdev.you.common.validator.intefaces.Teacher;

import javax.validation.constraints.Positive;

/**
 * 搜索 VO
 * Created in 2022/10/3 18:29
 *
 * @author Peter1303
 */
@Data
public class SearchVO {
    @Length(max = 60, groups = {Clazz.class})
    @Length(max = 6, groups = {Teacher.class, Student.class})
    private String name;

    /**
     * 学院 ID
     */
    @Positive
    private Long instituteId;

    /**
     * 校区 ID
     */
    @Positive
    private Long campusId;
}