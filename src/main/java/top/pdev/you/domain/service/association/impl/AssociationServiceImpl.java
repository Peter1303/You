package top.pdev.you.domain.service.association.impl;

import cn.hutool.core.date.DateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.event.AssociationAuditEvent;
import top.pdev.you.common.constant.AssociationStatus;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationAudit;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.association.AssociationAuditService;
import top.pdev.you.domain.service.association.AssociationManegeService;
import top.pdev.you.domain.service.association.AssociationProcessService;
import top.pdev.you.domain.service.association.AssociationService;
import top.pdev.you.domain.service.student.StudentService;
import top.pdev.you.domain.service.user.UserService;
import top.pdev.you.domain.ui.dto.AssociationBaseInfoDTO;
import top.pdev.you.domain.ui.dto.StudentInfoDTO;
import top.pdev.you.domain.ui.vm.AssociationAuditResponse;
import top.pdev.you.domain.ui.vm.AssociationInfoResponse;
import top.pdev.you.infrastructure.factory.AssociationFactory;
import top.pdev.you.infrastructure.factory.UserFactory;
import top.pdev.you.persistence.mapper.AssociationMapper;
import top.pdev.you.persistence.repository.AssociationAuditRepository;
import top.pdev.you.persistence.repository.AssociationManagerRepository;
import top.pdev.you.persistence.repository.AssociationParticipateRepository;
import top.pdev.you.persistence.repository.AssociationRepository;
import top.pdev.you.persistence.repository.StudentRepository;
import top.pdev.you.persistence.repository.UserRepository;
import top.pdev.you.web.association.command.AddAssociationCommand;
import top.pdev.you.web.association.command.ChangeNameCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.query.command.SearchCommand;
import top.pdev.you.web.user.command.AddAdminCommand;
import top.pdev.you.web.user.command.RemoveAdminCommand;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 社团服务实现类
 * Created in 2022/11/8 17:51
 *
 * @author Peter1303
 */
@Service
public class AssociationServiceImpl implements AssociationService {
    @Resource
    private AssociationProcessService associationProcessService;

    @Resource
    private AssociationManegeService associationManegeService;

    @Resource
    private AssociationAuditService associationAuditService;

    @Resource
    private UserService userService;

    @Resource
    private StudentService studentService;

    @Resource
    private AssociationRepository associationRepository;

    @Resource
    private AssociationAuditRepository associationAuditRepository;

    @Resource
    private AssociationParticipateRepository associationParticipateRepository;

    @Resource
    private AssociationManagerRepository associationManagerRepository;

    @Resource
    private AssociationFactory associationFactory;

    @Resource
    private UserRepository userRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private AssociationMapper associationMapper;

    @Resource
    private UserFactory userFactory;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<AssociationAuditResponse> auditList(User user) {
        RoleEntity role = userService.getRoleDomain(user);
        Long associationId = null;
        if (role instanceof Manager) {
            Manager manager = (Manager) role;
            Association association = belongAssociation(manager);
            associationId = association.getId();
        }
        // 没有过滤社团负责人的
        List<AssociationAudit> auditList = associationAuditRepository.findByAssociationIdAndStatusNull(associationId);
        return auditList.stream()
                .map(item -> {
                    Long aid = item.getAssociationId();
                    Association association = associationRepository.findById(aid);
                    AssociationAuditResponse auditVO = new AssociationAuditResponse();
                    auditVO.setId(item.getId());
                    auditVO.setName(association.getName());
                    auditVO.setTime(item.getTime());

                    Student student = studentRepository.findById(item.getStudentId());
                    StudentInfoDTO dto = new StudentInfoDTO();
                    dto.setName(student.getName());
                    dto.setClazz(studentService.getClazz(student));
                    dto.setNo(student.getNo());
                    dto.setCampus(studentService.getCampus(student));
                    dto.setInstitute(studentService.getInstitute(student));
                    auditVO.setStudent(dto);
                    return auditVO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void add(AddAssociationCommand addAssociationCommand) {
        Association association = associationFactory.newAssociation();
        association.setName(addAssociationCommand.getName());
        association.setSummary(addAssociationCommand.getSummary());
        // 查找是否已经存在社团
        if (associationRepository.existsByName(association.getName())) {
            throw new BusinessException("已经存在相同的社团");
        }
        if (!associationRepository.save(association)) {
            throw new BusinessException("无法保存社团");
        }
    }

    @Transactional
    @Override
    public void delete(IdCommand idCommand) {
        if (!associationRepository.deleteById(idCommand.getId())) {
            throw new BusinessException("无法删除社团");
        }
    }

    @Override
    public List<AssociationInfoResponse> list(User user, SearchCommand searchCommand) {
        // 出学生之外只能显示列表
        AtomicBoolean isStudent = new AtomicBoolean(false);
        AtomicReference<Long> studentId = new AtomicReference<>(null);
        try {
            Student student = userFactory.getStudent(user);
            studentId.set(student.getId());
            isStudent.set(true);
        } catch (Exception ignored) {
        }
        if (!Optional.ofNullable(searchCommand.getName()).isPresent()) {
            searchCommand.setName("");
        }
        return associationRepository.findNameContaining(searchCommand.getName())
                .stream()
                .map(item -> {
                    AssociationInfoResponse infoVO = new AssociationInfoResponse();
                    Long associationId = item.getId();
                    infoVO.setId(associationId);
                    infoVO.setName(item.getName());
                    infoVO.setSummary(item.getSummary());
                    infoVO.setNumbers(associationParticipateRepository.countByAssociationId(associationId));
                    if (isStudent.get()) {
                        int status = AssociationStatus.NOT;
                        // 有该学生即已经加入
                        Long id = studentId.get();
                        if (associationParticipateRepository.existsByStudentIdAndAssociationId(id, associationId)) {
                            status = AssociationStatus.JOINED;
                        } else {
                            AssociationAudit one =
                                    associationAuditRepository.findByStudentIdAndAssociationIdAndStatusNull(
                                            id, associationId);
                            // 如果存在审核记录
                            if (Optional.ofNullable(one).isPresent()) {
                                status = AssociationStatus.AUDIT;
                            }
                        }
                        infoVO.setStatus(status);
                    }
                    return infoVO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void join(boolean directly, User user, IdCommand idCommand) {
        // 需要审核
        if (!directly) {
            Long associationId = idCommand.getId();
            Student student = userFactory.getStudent(user);
            Long studentId = student.getId();
            boolean exists = associationParticipateRepository.existsByStudentIdAndAssociationId(studentId, associationId);
            if (exists) {
                throw new BusinessException("你已经加入该社团了");
            }
            // 是否已经在审核了
            AssociationAudit audit = associationAuditRepository.findByStudentIdAndAssociationIdAndStatusNull(studentId, associationId);
            if (Optional.ofNullable(audit).isPresent()) {
                throw new BusinessException("请等待审核");
            }
            AssociationAudit associationAudit = new AssociationAudit();
            associationAudit.setAssociationId(associationId);
            associationAudit.setStudentId(studentId);
            associationAudit.setTime(DateTime.now().toLocalDateTime());
            if (!associationAuditRepository.save(associationAudit)) {
                throw new BusinessException("无法保存加入社团审核");
            }
        }
    }

    /**
     * 检查审核
     *
     * @param auditDO 审核 DO
     */
    private void checkAudit(AssociationAudit auditDO) {
        if (Optional.ofNullable(auditDO.getStatus()).isPresent()) {
            throw new BusinessException("已经审核了");
        }
    }

    @Transactional
    @Override
    public void pass(IdCommand idCommand) {
        auditAssociationRequest(idCommand, true);
    }

    @Transactional
    @Override
    public void reject(IdCommand idCommand) {
        auditAssociationRequest(idCommand, false);
    }

    @Transactional
    @Override
    public void addManager(AddAdminCommand addAdminVO) {
        // TODO 老师添加负责人的时候只允许老师的社团
        addAdmin(addAdminVO.getAssociationId(), addAdminVO.getUid());
    }

    @Transactional
    @Override
    public void addAdmin(AddAdminCommand addAdminVO) {
        addAdmin(addAdminVO.getAssociationId(), addAdminVO.getUid());
    }

    @Transactional
    @Override
    public void setName(ChangeNameCommand nameVO) {
        Association association = associationRepository.findById(nameVO.getId());
        association.setName(nameVO.getName());
        if (!associationRepository.saveOrUpdate(association)) {
            throw new BusinessException("无法更改社团名字");
        }
    }

    @Transactional
    @Override
    public void setSummary(ChangeNameCommand nameVO) {
        Association association = associationRepository.findById(nameVO.getId());
        association.setSummary(nameVO.getName());
        if (!associationRepository.saveOrUpdate(association)) {
            throw new BusinessException("无法更改社团描述");
        }
    }

    @Transactional
    @Override
    public void removeManager(RemoveAdminCommand removeAdminVO) {
        removeAdmin(null, removeAdminVO.getUid());
    }

    @Transactional
    @Override
    public void removeAdmin(RemoveAdminCommand removeAdminVO) {
        removeAdmin(removeAdminVO.getAssociationId(), removeAdminVO.getUid());
    }

    @Override
    public Association belongAssociation(RoleEntity role) {
        User user = userService.getUser(role);
        List<AssociationManager> managers = associationManagerRepository.findByUserIdAndType(user.getId(),
                Permission.MANAGER.getValue());
        AssociationManager manager = managers.get(0);
        return associationRepository.findById(manager.getAssociationId());
    }

    private void addAdmin(Long associationId, Long userId) {
        Association association = associationRepository.findById(associationId);
        AssociationManager associationManager = associationFactory.newAssociationManger();
        associationManager.setAssociationId(associationId);
        User user = userRepository.findById(userId);
        RoleEntity role = userService.getRoleDomain(user);
        // 检查社团是否已经被相关的管理接管
        if (associationManegeService.exists(association, role)) {
            throw new BusinessException("已经存在了管理者");
        }
        associationManegeService.add(association, role);
        if (role instanceof Student) {
            // 让负责人直接进入社团
            associationProcessService.accept(association, (Student) role);
        }
    }

    private void removeAdmin(Long associationId, Long userId) {
        User user = userRepository.findById(userId);
        RoleEntity role = userService.getRoleDomain(user);
        // 如果是负责人直接获取管理的社团
        if (role instanceof Manager) {
            List<AssociationBaseInfoDTO> associations = associationMapper.getListByAdmin(user.getId());
            Optional<AssociationBaseInfoDTO> first = associations.stream().findFirst();
            if (first.isPresent()) {
                associationId = first.get().getId();
            }
        } else if (!(role instanceof Teacher)) {
            throw new BusinessException("没有这个管理者");
        }
        Optional.ofNullable(associationId).orElseThrow(() -> new BusinessException("没有社团 ID"));
        Association association = associationRepository.findById(associationId);
        AssociationManager associationManager = associationFactory.newAssociationManger();
        associationManager.setAssociationId(associationId);
        // 检查社团是否已经被相关的管理接管
        if (!associationManegeService.exists(association, role)) {
            throw new BusinessException("没有该管理者");
        }
        if (role instanceof Manager) {
            // 更改权限
            userService.permissionTo(user, Permission.USER);
        }
        associationManegeService.remove(association, role);
    }

    /**
     * 审计社团请求
     *
     * @param idCommand ID VO
     * @param accept    接受
     */
    private void auditAssociationRequest(IdCommand idCommand, boolean accept) {
        Long id = idCommand.getId();
        AssociationAudit audit = associationAuditRepository.findById(id);
        checkAudit(audit);
        Student student = studentRepository.findById(audit.getStudentId());
        Association association =
                associationRepository.findById(audit.getAssociationId());
        if (accept) {
            associationProcessService.accept(association, student);
            // 通过审核
            associationAuditService.changeStatus(audit, true);
        } else {
            // 审核拒绝
            associationAuditService.changeStatus(audit, true);
        }
        AssociationAuditEvent event = new AssociationAuditEvent(this);
        event.setAssociation(association);
        event.setStudent(student);
        event.setPassed(accept);
        // 推送事件
        applicationEventPublisher.publishEvent(event);
    }
}
