package top.pdev.you.domain.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.event.AssociationAuditEvent;
import top.pdev.you.common.constant.AssociationStatus;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationAudit;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.mapper.AssociationMapper;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.AssociationManagerRepository;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.AssociationService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.assembler.AssociationAssembler;
import top.pdev.you.interfaces.model.dto.AssociationBaseInfoDTO;
import top.pdev.you.interfaces.model.dto.StudentInfoDTO;
import top.pdev.you.interfaces.model.vo.AssociationAuditVO;
import top.pdev.you.interfaces.model.vo.AssociationInfoVO;
import top.pdev.you.interfaces.model.vo.req.AddAdminVO;
import top.pdev.you.interfaces.model.vo.req.AddAssociationVO;
import top.pdev.you.interfaces.model.vo.req.ChangeNameVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
import top.pdev.you.interfaces.model.vo.req.RemoveAdminVO;
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
    private AssociationManagerRepository associationManagerRepository;

    @Resource
    private AssociationFactory associationFactory;

    @Resource
    private UserRepository userRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private UserFactory userFactory;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Override
    public Result<?> add(AddAssociationVO addAssociationVO) {
        Association association = associationFactory.newAssociation();
        association.setName(addAssociationVO.getName());
        association.setSummary(addAssociationVO.getSummary());
        association.save();
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> delete(IdVO idVO) {
        Association association = associationRepository.findById(idVO.getId());
        association.delete();
        return Result.ok();
    }

    @Override
    public Result<?> list(User user, SearchVO searchVO) {
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

    @Transactional
    @Override
    public Result<?> join(boolean directly, User user, IdVO idVO) {
        // 需要审核
        if (!directly) {
            Long associationId = idVO.getId();
            Association association = associationRepository.findById(associationId);
            Student student = userFactory.getStudent(user);
            association.request(student);
        }
        return Result.ok();
    }

    @Override
    public Result<?> auditList(User user) {
        RoleEntity role = user.getRoleDomain();
        Long associationId = null;
        if (role instanceof Manager) {
            Manager manager = (Manager) role;
            Association association = manager.belongAssociation();
            associationId = association.getId();
        }
        // 没有过滤社团负责人的
        List<AssociationAudit> auditList = associationAuditRepository.findByAssociationIdAndStatusNull(associationId);
        List<AssociationAuditVO> list = auditList.stream()
                .map(item -> {
                    Long aid = item.getAssociationId();
                    Association association = associationRepository.findById(aid);
                    AssociationAuditVO auditVO = new AssociationAuditVO();
                    auditVO.setId(item.getId());
                    auditVO.setName(association.getName());
                    auditVO.setTime(item.getTime());

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

    @Transactional
    @Override
    public Result<?> pass(IdVO idVO) {
        auditAssociationRequest(idVO, true);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> reject(IdVO idVO) {
        auditAssociationRequest(idVO, false);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> addManager(AddAdminVO addAdminVO) {
        // TODO 老师添加负责人的时候只允许老师的社团
        addAdmin(addAdminVO.getAssociationId(), addAdminVO.getUid());
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> addAdmin(AddAdminVO addAdminVO) {
        addAdmin(addAdminVO.getAssociationId(), addAdminVO.getUid());
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> setName(ChangeNameVO nameVO) {
        Association association = associationRepository.findById(nameVO.getId());
        association.changeName(nameVO.getName());
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> setSummary(ChangeNameVO nameVO) {
        Association association = associationRepository.findById(nameVO.getId());
        association.changeSummary(nameVO.getName());
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> removeManager(RemoveAdminVO removeAdminVO) {
        removeAdmin(null, removeAdminVO.getUid());
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> removeAdmin(RemoveAdminVO removeAdminVO) {
        removeAdmin(removeAdminVO.getAssociationId(), removeAdminVO.getUid());
        return Result.ok();
    }

    private void addAdmin(Long associationId, Long userId) {
        AssociationManager associationManager = associationFactory.newAssociationManger();
        associationManager.setAssociationId(associationId);
        User user = userRepository.findById(userId);
        RoleEntity role = user.getRoleDomain();
        // 检查社团是否已经被相关的管理接管
        if (associationManager.exists(role)) {
            throw new BusinessException("已经存在了管理者");
        }
        associationManager.add(role);
        if (role instanceof Student) {
            // 让负责人直接进入社团
            Association association = associationRepository.findById(associationId);
            association.accept((Student) role);
        }
    }

    private void removeAdmin(Long associationId, Long userId) {
        User user = userRepository.findById(userId);
        RoleEntity role = user.getRoleDomain();
        // 如果是负责人直接获取管理的社团
        if (role instanceof Manager) {
            AssociationMapper associationMapper = SpringUtil.getBean(AssociationMapper.class);
            List<AssociationBaseInfoDTO> associations = associationMapper.getListByAdmin(user.getId());
            Optional<AssociationBaseInfoDTO> first = associations.stream().findFirst();
            if (first.isPresent()) {
                associationId = first.get().getId();
            }
        } else if (!(role instanceof Teacher)) {
            throw new BusinessException("没有这个管理者");
        }
        Optional.ofNullable(associationId).orElseThrow(() -> new BusinessException("没有社团 ID"));
        AssociationManager associationManager = associationFactory.newAssociationManger();
        associationManager.setAssociationId(associationId);
        // 检查社团是否已经被相关的管理接管
        if (!associationManager.exists(role)) {
            throw new BusinessException("没有该管理者");
        }
        associationManager.remove(role);
    }

    /**
     * 审计社团请求
     *
     * @param idVO   ID VO
     * @param accept 接受
     */
    private void auditAssociationRequest(IdVO idVO, boolean accept) {
        Long id = idVO.getId();
        AssociationAudit audit = associationAuditRepository.findById(id);
        checkAudit(audit);
        Student student = studentRepository.findById(audit.getStudentId());
        Association association =
                associationRepository.findById(audit.getAssociationId());
        if (accept) {
            association.accept(student);
            // 通过审核
            audit.pass();
        } else {
            // 审核拒绝
            audit.reject();
        }
        AssociationAuditEvent event = new AssociationAuditEvent(this);
        event.setAssociation(association);
        event.setStudent(student);
        event.setPassed(accept);
        // 推送事件
        applicationEventPublisher.publishEvent(event);
    }
}
