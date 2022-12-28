package top.pdev.you.domain.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import top.pdev.you.application.event.AssociationAuditEvent;
import top.pdev.you.common.constant.AssociationStatus;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.entity.types.TeacherId;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.repository.AssociationAuditRepository;
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
    private UserFactory userFactory;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Result<?> add(AddAssociationVO addAssociationVO) {
        Association association = associationFactory.newAssociation();
        AssociationDO associationDO = new AssociationDO();
        associationDO.setName(addAssociationVO.getName());
        associationDO.setSummary(addAssociationVO.getSummary());
        association.save(associationDO);
        return Result.ok();
    }

    @Override
    public Result<?> delete(IdVO idVO) {
        Association association = associationRepository.find(new AssociationId(idVO.getId()));
        association.delete();
        return Result.ok();
    }

    @Override
    public Result<?> list(SearchVO searchVO, TokenInfo tokenInfo) {
        Long uid = tokenInfo.getUid();
        User user = userRepository.find(new UserId(uid));
        // 出学生之外只能显示列表
        AtomicBoolean isStudent = new AtomicBoolean(false);
        AtomicReference<StudentId> studentId = new AtomicReference<>(null);
        try {
            Student student = userFactory.getStudent(user);
            studentId.set(student.getStudentId());
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
                                StudentId id = studentId.get();
                                if (Objects.equals(item.getStudentId(), id.getId())) {
                                    status = AssociationStatus.JOINED;
                                } else {
                                    // 如果存在审核记录那么需要检查是否通过
                                    AssociationAuditDO one = associationAuditRepository.getOne(id,
                                            new AssociationId(item.getId()));
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
            AssociationId associationId = new AssociationId(idVO.getId());
            Association association = associationRepository.find(associationId);
            User user = userRepository.find(new UserId(tokenInfo.getUid()));
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
                    Student student = studentRepository.find(new StudentId(item.getStudentId()));
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
    private void checkAudit(AssociationAuditDO auditDO) {
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
        addAdmin(new AssociationId(addAdminVO.getAssociationId()),
                new StudentId(addAdminVO.getUid()));
        return Result.ok();
    }

    @Override
    public Result<?> addAdmin(AddAdminVO addAdminVO) {
        addAdmin(new AssociationId(addAdminVO.getAssociationId()),
                new TeacherId(addAdminVO.getUid()));
        return Result.ok();
    }

    private void addAdmin(AssociationId associationId, Id id) {
        Association association =
                associationRepository.getOne(associationId);
        User user = userRepository.find(new UserId(id.getId()));
        // 检查社团是否已经被相关的管理接管
        if (association.adminExists(user)) {
            throw new BusinessException("已经存在了管理者");
        }
        Optional.ofNullable(user)
                .orElseThrow(() -> new BusinessException("没有找到用户"));
        id.setId(user.getTargetId());
        // 负责人
        if (id instanceof StudentId) {
            Student student = studentRepository.find((StudentId) id);
            association.addAdmin(student);
        }
        // 指导老师
        if (id instanceof TeacherId) {
            Teacher teacher = teacherRepository.find((TeacherId) id);
            association.addAdmin(teacher);
        }
    }

    /**
     * 审计社团请求
     *
     * @param idVO   ID VO
     * @param accept 接受
     */
    private void auditAssociationRequest(IdVO idVO, boolean accept) {
        Id id = new Id(idVO.getId());
        AssociationAuditDO auditDO = associationAuditRepository.getOne(id);
        checkAudit(auditDO);
        Student student = studentRepository.find(new StudentId(auditDO.getStudentId()));
        Association association =
                associationRepository.find(new AssociationId(auditDO.getAssociationId()));
        if (accept) {
            association.accept(student);
        }
        // 更改审核记录
        associationAuditRepository.changeStatus(id, accept);
        AssociationAuditEvent event = new AssociationAuditEvent(this);
        event.setAssociation(association);
        event.setStudent(student);
        event.setPassed(accept);
        // 推送事件
        applicationEventPublisher.publishEvent(event);
    }
}
