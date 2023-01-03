package top.pdev.you.domain.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.data.AssociationParticipantDO;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.AssociationManagerRepository;
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
    private Long id;

    private String name;

    private String summary;

    private AssociationDO associationDO;

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
        this.associationDO = associationDO;
        this.id = associationDO.getId();
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
        if (associationRepository.existsByName(associationDO.getName())) {
            throw new BusinessException("已经存在相同的社团");
        }
        if (!associationRepository.save(associationDO)) {
            throw new BusinessException("无法保存社团");
        }
        setId(associationDO.getId());
        setName(associationDO.getName());
        setSummary(associationDO.getSummary());
    }

    /**
     * 请求加入
     *
     * @param student 学生
     */
    public void request(Student student) {
        Long studentId = student.getId();
        boolean exists = associationParticipateRepository.existsByStudentIdAndAssociationId(studentId, id);
        if (exists) {
            throw new BusinessException("你已经加入该社团了");
        }
        AssociationAuditDO associationAuditDO = new AssociationAuditDO();
        associationAuditDO.setAssociationId(id);
        associationAuditDO.setStudentId(studentId);
        associationAuditDO.setTime(DateTime.now().toLocalDateTime());
        // 是否已经在审核了
        if (associationAuditRepository.existsByStudentIdAndAssociationId(studentId, id)) {
            throw new BusinessException("请等待审核");
        }
        if (!associationAuditRepository.save(associationAuditDO)) {
            throw new BusinessException("无法保存加入社团审核");
        }
    }

    /**
     * 接受入社
     *
     * @param student 学生
     */
    public void accept(Student student) {
        AssociationParticipantDO associationParticipantDO = new AssociationParticipantDO();
        associationParticipantDO.setAssociationId(this.getId());
        associationParticipantDO.setStudentId(student.getId());
        if (!associationParticipateRepository.save(associationParticipantDO)) {
            throw new BusinessException("加入社团失败");
        }
    }

    /**
     * 删除
     */
    public void delete() {
        if (!associationRepository.removeById(id)) {
            throw new BusinessException("无法删除社团");
        }
    }

    /**
     * 改变名字
     *
     * @param name 名字
     */
    public void changeName(String name) {
        setName(name);
        associationDO.setName(name);
        update("无法更改社团名字");
    }

    /**
     * 更改描述
     *
     * @param summary 描述
     */
    public void changeSummary(String summary) {
        setSummary(summary);
        associationDO.setSummary(summary);
        update("无法更改社团描述");
    }

    /**
     * 更新
     *
     * @param errorMsg 错误味精
     */
    private void update(String errorMsg) {
        if (!associationRepository.saveOrUpdate(associationDO)) {
            throw new BusinessException(errorMsg);
        }
    }
}
