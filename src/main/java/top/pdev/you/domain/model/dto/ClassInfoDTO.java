package top.pdev.you.domain.model.dto;

import lombok.Data;

/**
 * 班级信息 DTO
 * Created in 2022/10/3 18:40
 *
 * @author Peter1303
 */
@Data
public class ClassInfoDTO {
    private Long id;
    private String name;
    private String campus;
    private String institute;
    private Integer grade;
}
