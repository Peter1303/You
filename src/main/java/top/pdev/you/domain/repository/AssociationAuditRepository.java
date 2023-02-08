package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.AssociationAudit;

import java.util.List;

/**
 * 社团审核仓库
 * Created in 2022/11/9 13:10
 *
 * @author Peter1303
 */
public interface AssociationAuditRepository extends IService<AssociationAudit> {
    /**
     * 获取一个
     *
     * @param id ID
     * @return {@link AssociationAudit}
     */
    AssociationAudit findById(Long id);

    /**
     * 通过学生 ID和社团 ID和状态不为空查找
     * 通过
     *
     * @param studentId     学生 ID
     * @param associationId 社团 ID
     * @return boolean
     */
    AssociationAudit findByStudentIdAndAssociationIdAndStatusNull(Long studentId, Long associationId);

    /**
     * 通过社团 ID 查找
     *
     * @param associationId 协会 ID
     * @return {@link List}<{@link AssociationAudit}>
     */
    List<AssociationAudit> findByAssociationIdAndStatusNull(Long associationId);
}
