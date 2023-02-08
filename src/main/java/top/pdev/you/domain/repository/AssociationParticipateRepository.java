package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.data.AssociationParticipantDO;

import java.util.List;

/**
 * 社团成员仓库
 * Created in 2022/11/9 15:39
 *
 * @author Peter1303
 */
public interface AssociationParticipateRepository
        extends IService<AssociationParticipantDO> {
    /**
     * 获取参与列表
     *
     * @param student 学生
     * @return {@link List}<{@link AssociationParticipantDO}>
     */
    List<AssociationParticipantDO> getParticipateList(Student student);

    /**
     * 通过社团 ID 查找
     *
     * @param associationId 社团 ID
     * @return {@link List}<{@link AssociationParticipantDO}>
     */
    List<AssociationParticipantDO> findByAssociationId(Long associationId);

    /**
     * 通过 社团ID 统计
     *
     * @param id ID
     * @return long
     */
    long countByAssociationId(long id);

    /**
     * 通过社团 ID和学生 ID 删除
     *
     * @param associationId 协会 ID
     * @param studentId     学生 ID
     * @return boolean
     */
    boolean deleteByAssociationIdAndStudentId(Long associationId, Long studentId);

    /**
     * 存在
     *
     * @param studentId     学生 ID
     * @param associationId 社团 ID
     * @return boolean
     */
    boolean existsByStudentIdAndAssociationId(Long studentId, Long associationId);
}
