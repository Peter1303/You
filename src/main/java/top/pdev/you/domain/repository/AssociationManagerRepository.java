package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.data.AssociationManagerDO;

import java.util.List;

/**
 * 社团管理仓库
 * Created in 2022/10/24 17:00
 *
 * @author Peter1303
 */
public interface AssociationManagerRepository extends IService<AssociationManagerDO> {
    /**
     * 获取
     *
     * @param student 学生
     * @return {@link AssociationManagerDO}
     */
    AssociationManagerDO getOne(Student student);

    /**
     * 获取管理列表
     *
     * @param teacher 老师
     * @return {@link List}<{@link AssociationManagerDO}>
     */
    List<AssociationManagerDO> getManagedList(Teacher teacher);
}
