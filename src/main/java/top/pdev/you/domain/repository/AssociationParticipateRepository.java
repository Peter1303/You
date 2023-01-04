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
     * 存在
     *
     * @param studentId     学生 ID
     * @param associationId 社团 ID
     * @return boolean
     */
    boolean existsByStudentIdAndAssociationId(Long studentId, Long associationId);
}
