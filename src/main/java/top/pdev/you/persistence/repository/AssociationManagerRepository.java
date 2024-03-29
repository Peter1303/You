package top.pdev.you.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Teacher;

import java.util.List;

/**
 * 社团管理仓库
 * Created in 2022/10/24 17:00
 *
 * @author Peter1303
 */
public interface AssociationManagerRepository extends IService<AssociationManager> {
    /**
     * 通过用户 ID 和类型查找
     *
     * @param id   ID
     * @param type 类型
     * @return {@link AssociationManager}
     */
    List<AssociationManager> findByUserIdAndType(Long id, Integer type);

    /**
     * 获取管理列表
     *
     * @param teacher 老师
     * @return {@link List}<{@link AssociationManager}>
     */
    List<AssociationManager> getManagedList(Teacher teacher);

    /**
     * 通过社团和用户 ID 删除
     *
     * @param associationId 协会 ID
     * @param id            ID
     * @return boolean
     */
    boolean deleteByAssociationAndUserId(Long associationId, Long id);
}
