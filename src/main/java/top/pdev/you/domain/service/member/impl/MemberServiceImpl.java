package top.pdev.you.domain.service.member.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationParticipant;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.model.vm.StudentInfoResponse;
import top.pdev.you.domain.service.association.AssociationService;
import top.pdev.you.domain.service.member.MemberService;
import top.pdev.you.domain.service.student.StudentService;
import top.pdev.you.domain.service.user.UserService;
import top.pdev.you.infrastructure.factory.UserFactory;
import top.pdev.you.persistence.repository.AssociationParticipateRepository;
import top.pdev.you.persistence.repository.StudentRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 社员服务实现类
 * Created in 2023/1/4 22:18
 *
 * @author Peter1303
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private UserService userService;

    @Resource
    private AssociationService associationService;

    @Resource
    private StudentService studentService;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private AssociationParticipateRepository associationParticipateRepository;

    @Resource
    private UserFactory userFactory;

    @Override
    public List<StudentInfoResponse> list(User user) {
        Manager manager = userFactory.getManager(user);
        Association association = associationService.belongAssociation(manager);
        List<AssociationParticipant> all = associationParticipateRepository.findByAssociationId(association.getId());
        List<Student> participants = all.stream().map(participant ->
                studentRepository.findById(participant.getStudentId())
        ).collect(Collectors.toList());
        return participants.stream()
                .filter(student -> !Objects.equals(student.getUserId(), user.getId()))
                .map(student -> {
                    StudentInfoResponse infoVO = new StudentInfoResponse();
                    infoVO.setStudentId(student.getId());
                    infoVO.setName(student.getName());
                    infoVO.setClazz(studentService.getClazz(student));
                    infoVO.setInstitute(studentService.getInstitute(student));
                    infoVO.setCampus(studentService.getCampus(student));
                    return infoVO;
                }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void remove(User user, IdCommand idCommand) {
        Long studentId = idCommand.getId();
        // 目标学生
        Student student = studentRepository.findById(studentId);
        RoleEntity role = userService.getRoleDomain(user);
        Manager manager = (Manager) role;
        // 用户管理的社团
        Association association = associationService.belongAssociation(manager);
        Long associationId = association.getId();
        if (Objects.equals(manager.getId(), studentId)) {
            throw new BusinessException("不可以剔除自己");
        }
        // 是否在社团
        if (!associationParticipateRepository.existsByStudentIdAndAssociationId(studentId, associationId)) {
            throw new BusinessException("没有这个成员");
        }
        // 踢出
        boolean ok = associationParticipateRepository.deleteByAssociationIdAndStudentId(
                associationId,
                student.getId()
        );
        if (!ok) {
            throw new BusinessException("无法移出社团");
        }
    }
}
