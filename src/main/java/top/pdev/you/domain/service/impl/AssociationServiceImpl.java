package top.pdev.you.domain.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import top.pdev.you.application.event.AssociationAuditEvent;
import top.pdev.you.common.constant.AssociationStatus;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationAudit;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.type.Id;
import top.pdev.you.domain.entity.type.StudentId;
import top.pdev.you.domain.entity.type.TeacherId;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.AssociationManagerRepository;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.domain.repository.TeacherRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.AssociationService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.assembler.AssociationAssembler;
import top.pdev.you.interfaces.model.dto.AssociationAuditDTO;
import top.pdev.you.interfaces.model.dto.StudentInfoDTO;
import top.pdev.you.interfaces.model.vo.AssociationAuditVO;
import top.pdev.you.interfaces.model.vo.AssociationInfoVO;
import top.pdev.you.interfaces.model.vo.req.AddAdminVO;
import top.pdev.you.interfaces.model.vo.req.AddAssociationVO;
import top.pdev.you.interfaces.model.vo.req.ChangeNameVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
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
    private AssociationRepository associationRepository;

    @Resource
    private AssociationAuditRepository associationAuditRepository;

    @Resource
    private AssociationFactory associationFactory;

    @Resource
    private UserRepository userRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private TeacherRepository teacherRepository;

    @Resource
    private AssociationManagerRepository associationManagerRepository;

    @Resource
    private UserFactory userFactory;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Result<?> add(AddAssociationVO addAssociationVO) {
        Association association = associationFactory.newAssociation();
        association.setName(addAssociationVO.getName());
        association.setSummary(addAssociationVO.getSummary());
        association.save();
        return Result.ok();
    }

    @Override
    public Result<?> delete(IdVO idVO) {
        Association association = associationRepository.findById(idVO.getId());
        association.delete();
        return Result.ok();
    }

    @Override
    public Result<?> list(SearchVO searchVO, TokenInfo tokenInfo) {
        Long uid = tokenInfo.getUid();
        User user = userRepository.findById(uid);
        // 出学生之外只能显示列表
        AtomicBoolean isStudent = new AtomicBoolean(false);
        AtomicReference<Long> studentId = new AtomicReference<>(null);
        try {
            Student student = userFactory.getStudent(user);
            studentId.set(student.getId());
            isStudent.set(true);
        } catch (Exception ignored) {
        }
        List<AssociationInfoVO> list =
                associationRepository.getInfoList(searchVO)
                        .stream()
                        .map(item -> {
                            AssociationInfoVO infoVO =
                                    AssociationAssembler.INSTANCE.convert2infoVO(item);
                            if (isStudent.get()) {
                                int status = AssociationStatus.NOT;
                                // 有该学生即已经加入
                                Long id = studentId.get();
                                if (Objects.equals(item.getStudentId(), id)) {
                                    status = AssociationStatus.JOINED;
                                } else {
                                    AssociationAudit one =
                                            associationAuditRepository.findByStudentIdAndAssociationIdAndStatusNull(
                                                    id, item.getId());
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
        return Result.ok(list);
    }

    @Override
    public Result<?> join(boolean directly, TokenInfo tokenInfo, IdVO idVO) {
        // 需要审核
        if (!directly) {
            Long associationId = idVO.getId();
            Association association = associationRepository.findById(associationId);
            User user = userRepository.findById(tokenInfo.getUid());
            Student student = userFactory.getStudent(user);
            association.request(student);
        }
        return Result.ok();
    }

    @Override
    public Result<?> auditList() {
        List<AssociationAuditDTO> auditList = associationAuditRepository.getAuditList();
        List<AssociationAuditVO> list = auditList.stream()
                .map(item -> {
                    AssociationAuditVO auditVO = AssociationAssembler.INSTANCE.convert2auditInfoVO(item);
                    Student student = studentRepository.findById(item.getStudentId());
                    StudentInfoDTO dto = new StudentInfoDTO();
                    dto.setName(student.getName());
                    dto.setClazz(student.getClazz());
                    dto.setNo(student.getNo());
                    dto.setCampus(student.getCampus());
                    dto.setInstitute(student.getInstitute());
                    auditVO.setStudent(dto);
                    return auditVO;
                })
                .collect(Collectors.toList());
        return Result.ok(list);
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

    @Override
    public Result<?> pass(IdVO idVO) {
        auditAssociationRequest(idVO, true);
        return Result.ok();
    }

    @Override
    public Result<?> reject(IdVO idVO) {
        auditAssociationRequest(idVO, false);
        return Result.ok();
    }

    @Override
    public Result<?> addManager(AddAdminVO addAdminVO) {
        addAdmin(addAdminVO.getAssociationId(), new StudentId(addAdminVO.getUid()));
        return Result.ok();
    }

    @Override
    public Result<?> addAdmin(AddAdminVO addAdminVO) {
        addAdmin(addAdminVO.getAssociationId(), new TeacherId(addAdminVO.getUid()));
        return Result.ok();
    }

    @Override
    public Result<?> setName(ChangeNameVO nameVO) {
        Association association = associationRepository.findById(nameVO.getId());
        association.changeName(nameVO.getName());
        return Result.ok();
    }

    @Override
    public Result<?> setSummary(ChangeNameVO nameVO) {
        Association association = associationRepository.findById(nameVO.getId());
        association.changeSummary(nameVO.getName());
        return Result.ok();
    }

    private void addAdmin(Long associationId, Id id) {
        AssociationManager associationManager =
                associationManagerRepository.findByAssociationId(associationId);
        User user = null;
        // 负责人
        if (id instanceof StudentId) {
            Student student = studentRepository.findById(id.getId());
            associationManager.addAdmin(student);
            user = userRepository.findById(student.getUserId());
        }
        // 指导老师
        if (id instanceof TeacherId) {
            Teacher teacher = teacherRepository.findById(id.getId());
            user = userRepository.findById(teacher.getUserId());
            associationManager.addAdmin(teacher);
        }
        // 检查社团是否已经被相关的管理接管
        if (associationManager.adminExists(user)) {
            throw new BusinessException("已经存在了管理者");
        }
    }

    /**
     * 审计社团请求
     *
     * @param idVO   ID VO
     * @param accept 接受
     */
    private void auditAssociationRequest(IdVO idVO, boolean accept) {
        Long id = idVO.getId();
        AssociationAudit auditDO = associationAuditRepository.findById(id);
        checkAudit(auditDO);
        Student student = studentRepository.findById(auditDO.getStudentId());
        Association association =
                associationRepository.findById(auditDO.getAssociationId());
        if (accept) {
            association.accept(student);
        }
        // 更改审核记录
        AssociationAudit audit = associationAuditRepository.findById(id);
        audit.setStatus(accept);
        boolean update = associationAuditRepository.updateById(audit);
        if (!update) {
            throw new BusinessException("无法更改入团审核状态");
        }
        AssociationAuditEvent event = new AssociationAuditEvent(this);
        event.setAssociation(association);
        event.setStudent(student);
        event.setPassed(accept);
        // 推送事件
        applicationEventPublisher.publishEvent(event);
    }
}
