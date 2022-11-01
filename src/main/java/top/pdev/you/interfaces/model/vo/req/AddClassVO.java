package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;

/**
 * 添加班级 VO
 * Created in 2022/11/1 15:26
 *
 * @author Peter1303
 */
@Data
public class AddClassVO {
    private String name;
    private Integer grade;
    private Long campusId;
    private Long instituteId;
}
