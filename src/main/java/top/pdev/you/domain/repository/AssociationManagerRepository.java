package top.pdev.you.domain.repository;

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
     * 通过用户 ID 查找
     *
     * @param id ID
     * @return {@link AssociationManager}
     */
    AssociationManager findByUserId(Long id);

    /**
     * 获取管理列表
     *
     * @param teacher 老师
     * @return {@link List}<{@link AssociationManager}>
     */
    List<AssociationManager> getManagedList(Teacher teacher);

    /**
     * 通过协会 ID和用户 ID 是否存在
     *
     * @param associationId 社团 ID
     * @param userId        用户 ID
     * @param permission    权限
     * @return boolean
     */
    boolean existsByAssociationIdAndUserIdAndType(Long associationId,
                                                  Long userId,
                                                  Integer permission);

    /**
     * 通过社团和用户 ID 删除
     *
     * @param associationId 协会 ID
     * @param id            ID
     * @return boolean
     */
    boolean deleteByAssociationAndUserId(Long associationId, Long id);
}
