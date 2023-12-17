package top.pdev.you.application.service.association;

import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;

/**
 * 社团处理服务
 * Created in 2023/12/13 20:27
 *
 * @author Peter1303
 */
public interface AssociationProcessService {
    /**
     * 接受
     *
     * @param association 协会
     * @param student     学生
     */
    void accept(Association association, Student student);
}
