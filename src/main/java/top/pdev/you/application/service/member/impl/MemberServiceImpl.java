package top.pdev.you.application.service.member.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.member.MemberService;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.repository.AssociationParticipateRepository;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.domain.ui.vm.StudentInfoResponse;
import top.pdev.you.web.command.IdCommand;

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
    private StudentRepository studentRepository;

    @Resource
    private AssociationParticipateRepository associationParticipateRepository;

    @Resource
    private UserFactory userFactory;

    @Override
    public Result<?> list(User user) {
        Manager manager = userFactory.getManager(user);
        Association association = manager.belongAssociation();
        List<Student> participants = association.participants();
        List<StudentInfoResponse> list = participants.stream()
                .filter(student -> !Objects.equals(student.getUserId(), user.getId()))
                .map(student -> {
                    StudentInfoResponse infoVO = new StudentInfoResponse();
                    infoVO.setStudentId(student.getId());
                    infoVO.setName(student.getName());
                    infoVO.setClazz(student.getClazz());
                    infoVO.setInstitute(student.getInstitute());
                    infoVO.setCampus(student.getCampus());
                    return infoVO;
                }).collect(Collectors.toList());
        return Result.ok(list);
    }

    @Transactional
    @Override
    public Result<?> remove(User user, IdCommand idCommand) {
        Long studentId = idCommand.getId();
        // 目标学生
        Student student = studentRepository.findById(studentId);
        RoleEntity role = user.getRoleDomain();
        Manager manager = (Manager) role;
        // 用户管理的社团48
        Association association = manager.belongAssociation();
        Long associationId = association.getId();
        if (Objects.equals(manager.getId(), studentId)) {
            throw new BusinessException("不可以剔除自己");
        }
        // 是否在社团
        if (!associationParticipateRepository.existsByStudentIdAndAssociationId(studentId, associationId)) {
            throw new BusinessException("没有这个成员");
        }
        // 踢出
        association.kick(student);
        return Result.ok();
    }
}
