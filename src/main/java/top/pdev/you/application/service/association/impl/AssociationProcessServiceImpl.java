package top.pdev.you.application.service.association.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.association.AssociationProcessService;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationParticipant;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.persistence.repository.AssociationParticipateRepository;

import javax.annotation.Resource;

/**
 * 社团处理实现类
 * Created in 2023/12/13 20:27
 *
 * @author Peter1303
 */
@Service
public class AssociationProcessServiceImpl implements AssociationProcessService {
    @Resource
    private AssociationParticipateRepository associationParticipateRepository;

    /**
     * 接受入社
     *
     * @param student     学生
     * @param association 协会
     */
    @Transactional
    @Override
    public void accept(Association association, Student student) {
        Long associationId = association.getId();
        Long studentId = student.getId();
        AssociationParticipant associationParticipant = new AssociationParticipant();
        associationParticipant.setAssociationId(associationId);
        associationParticipant.setStudentId(studentId);
        // 跳过已经加了
        if (associationParticipateRepository.existsByStudentIdAndAssociationId(studentId, associationId)) {
            return;
        }
        if (!associationParticipateRepository.save(associationParticipant)) {
            throw new BusinessException("加入社团失败");
        }
    }
}
