package top.pdev.you.domain.service.teacher;

import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Teacher;

import java.util.List;

/**
 * 老师服务
 * Created in 2023/12/16 11:15
 *
 * @author Peter1303
 */
public interface TeacherService {
    /**
     * 获取管理社团列表
     *
     * @param teacher 老师
     * @return {@link List}<{@link Association}>
     */
    List<Association> getManagedAssociationList(Teacher teacher);
}
