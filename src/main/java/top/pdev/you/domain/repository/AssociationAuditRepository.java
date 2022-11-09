package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.StudentId;

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
     * 存在
     *
     * @param studentId 学生 ID
     * @param id        ID
     * @return boolean
     */
    boolean exists(StudentId studentId, AssociationId id);
}
