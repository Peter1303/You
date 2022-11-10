package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.interfaces.model.dto.AssociationAuditDTO;

import java.util.List;

/**
 * 社团审核仓库
 * Created in 2022/11/9 13:10
 *
 * @author Peter1303
 */
public interface AssociationAuditRepository extends IService<AssociationAuditDO> {
    /**
     * 获取一个
     *
     * @param studentId     学生 ID
     * @param associationId 社团 ID
     * @return boolean
     */
    AssociationAuditDO getOne(StudentId studentId, AssociationId associationId);

    /**
     * 获取一个
     *
     * @param id ID
     * @return {@link AssociationAuditDO}
     */
    AssociationAuditDO getOne(Id id);

    /**
     * 获取审核列表
     *
     * @return {@link List}<{@link AssociationAuditDTO}>
     */
    List<AssociationAuditDTO> getAuditList();

    /**
     * 存在
     *
     * @param studentId 学生 ID
     * @param id        ID
     * @return boolean
     */
    boolean exists(StudentId studentId, AssociationId id);

    /**
     * 获取审核列表
     *
     * @return {@link List}<{@link AssociationAuditDTO}>
     */
    List<AssociationAuditDTO> getAuditList();
}
