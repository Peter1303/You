package top.pdev.you.web.query.command;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.pdev.you.common.validator.intefaces.Association;
import top.pdev.you.common.validator.intefaces.Campus;
import top.pdev.you.common.validator.intefaces.Clazz;
import top.pdev.you.common.validator.intefaces.Institute;
import top.pdev.you.common.validator.intefaces.Student;
import top.pdev.you.common.validator.intefaces.Teacher;

import javax.validation.constraints.Positive;

/**
 * 搜索命令
 * Created in 2022/10/3 18:29
 *
 * @author Peter1303
 */
@Data
public class SearchCommand {
    @Length(max = 50, groups = {Association.class})
    @Length(max = 60, groups = {
            Clazz.class,
            Campus.class,
            Institute.class
    })
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
