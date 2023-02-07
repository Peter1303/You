package top.pdev.you.domain.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.AssociationParticipantDO;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.AssociationParticipateRepository;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 社团
 * Created in 2022/10/24 14:04
 *
 * @author Peter1303
 */
@TableName("association")
@Data
public class Association extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */

    private String summary;

    /**
     * 保存
     */
    public void save() {
        AssociationRepository associationRepository = SpringUtil.getBean(AssociationRepository.class);
        // 查找是否已经存在社团
        if (associationRepository.existsByName(getName())) {
            throw new BusinessException("已经存在相同的社团");
        }
        if (!associationRepository.save(this)) {
            throw new BusinessException("无法保存社团");
        }
        setId(getId());
        setName(getName());
        setSummary(getSummary());
    }

    /**
     * 请求加入
     *
     * @param student 学生
     */
    public void request(Student student) {
        Long studentId = student.getId();
        AssociationParticipateRepository associationParticipateRepository =
                SpringUtil.getBean(AssociationParticipateRepository.class);
        AssociationAuditRepository associationAuditRepository =
                SpringUtil.getBean(AssociationAuditRepository.class);
        boolean exists = associationParticipateRepository.existsByStudentIdAndAssociationId(studentId, id);
        if (exists) {
            throw new BusinessException("你已经加入该社团了");
        }
        // 是否已经在审核了
        AssociationAudit audit = associationAuditRepository.findByStudentIdAndAssociationIdAndStatusNull(studentId, id);
        if (Optional.ofNullable(audit).isPresent()) {
            throw new BusinessException("请等待审核");
        }
        AssociationAudit associationAudit = new AssociationAudit();
        associationAudit.setAssociationId(id);
        associationAudit.setStudentId(studentId);
        associationAudit.setTime(DateTime.now().toLocalDateTime());
        if (!associationAuditRepository.save(associationAudit)) {
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
        AssociationParticipateRepository associationParticipateRepository =
                SpringUtil.getBean(AssociationParticipateRepository.class);
        // 跳过已经加了
        if (associationParticipateRepository.existsByStudentIdAndAssociationId(student.getId(), getId())) {
            return;
        }
        if (!associationParticipateRepository.save(associationParticipantDO)) {
            throw new BusinessException("加入社团失败");
        }
    }

    /**
     * 删除
     */
    public void delete() {
        AssociationRepository associationRepository =
                SpringUtil.getBean(AssociationRepository.class);
        if (!associationRepository.deleteById(id)) {
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
        update("无法更改社团名字");
    }

    /**
     * 更改描述
     *
     * @param summary 描述
     */
    public void changeSummary(String summary) {
        setSummary(summary);
        update("无法更改社团描述");
    }

    /**
     * 更新
     *
     * @param errorMsg 错误味精
     */
    private void update(String errorMsg) {
        AssociationRepository associationRepository =
                SpringUtil.getBean(AssociationRepository.class);
        if (!associationRepository.saveOrUpdate(this)) {
            throw new BusinessException(errorMsg);
        }
    }

    /**
     * 踢除
     *
     * @param student 学生
     */
    public void kick(Student student) {
        notNull(Association::getId);
        AssociationParticipateRepository associationParticipateRepository =
                SpringUtil.getBean(AssociationParticipateRepository.class);
        boolean ok = associationParticipateRepository.deleteByAssociationIdAndStudentId(
                getId(),
                student.getId()
        );
        if (!ok) {
            throw new BusinessException("无法移出社团");
        }
    }

    /**
     * 参与者
     *
     * @return {@link List}<{@link Student}>
     */
    public List<Student> participants() {
        notNull(Association::getId);
        AssociationParticipateRepository associationParticipateRepository =
                SpringUtil.getBean(AssociationParticipateRepository.class);
        StudentRepository studentRepository = SpringUtil.getBean(StudentRepository.class);
        List<AssociationParticipantDO> all = associationParticipateRepository.findByAssociationId(getId());
        return all.stream().map(participant ->
                studentRepository.findById(participant.getStudentId())
        ).collect(Collectors.toList());
    }
}
