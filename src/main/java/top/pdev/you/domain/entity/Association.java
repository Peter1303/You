package top.pdev.you.domain.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.AssociationParticipateRepository;
import top.pdev.you.domain.repository.AssociationRepository;

import java.util.Optional;

/**
 * 社团
 * Created in 2022/10/24 14:04
 *
 * @author Peter1303
 */
@Data
public class Association extends BaseEntity {
    private AssociationId id;

    private String name;

    private String summary;

    @Getter(AccessLevel.NONE)
    private final AssociationRepository associationRepository =
            SpringUtil.getBean(AssociationRepository.class);

    @Getter(AccessLevel.NONE)
    private final AssociationAuditRepository associationAuditRepository =
            SpringUtil.getBean(AssociationAuditRepository.class);

    @Getter(AccessLevel.NONE)
    private final AssociationParticipateRepository associationParticipateRepository =
            SpringUtil.getBean(AssociationParticipateRepository.class);

    public Association(AssociationDO associationDO) {
        if (!Optional.ofNullable(associationDO).isPresent()) {
            return;
        }
        this.id = new AssociationId(associationDO.getId());
        this.name = associationDO.getName();
        this.summary = associationDO.getSummary();
    }

    /**
     * 保存
     *
     * @param associationDO 协会 DO
     */
    public void save(AssociationDO associationDO) {
        // 查找是否已经存在社团
        if (associationRepository.exists(associationDO.getName())) {
            throw new BusinessException("已经存在相同的社团");
        }
        if (!associationRepository.save(associationDO)) {
            throw new BusinessException("无法保存社团");
        }
        setId(new AssociationId(associationDO.getId()));
        setName(associationDO.getName());
        setSummary(associationDO.getSummary());
    }

    /**
     * 请求加入
     *
     * @param student 学生
     */
    public void request(Student student) {
        StudentId studentId = student.getStudentId();
        boolean exists = associationParticipateRepository.exists(studentId, id);
        if (exists) {
            throw new BusinessException("你已经加入该社团了");
        }
        AssociationAuditDO associationAuditDO = new AssociationAuditDO();
        associationAuditDO.setAssociationId(id.getId());
        associationAuditDO.setStudentId(studentId.getId());
        associationAuditDO.setTime(DateTime.now().toLocalDateTime());
        // 是否已经在审核了
        if (associationAuditRepository.exists(studentId, id)) {
            throw new BusinessException("请等待审核");
        }
        if (!associationAuditRepository.save(associationAuditDO)) {
            throw new BusinessException("无法保存加入社团审核");
        }
    }
}
