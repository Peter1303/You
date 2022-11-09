package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.constant.AssociationStatus;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.data.AssociationAuditDO;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.factory.AssociationFactory;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.repository.AssociationAuditRepository;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.AssociationService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.assembler.AssociationAssembler;
import top.pdev.you.interfaces.model.vo.AssociationInfoVO;
import top.pdev.you.interfaces.model.vo.req.AddAssociationVO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    private UserFactory userFactory;

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
    public Result<?> list(SearchVO searchVO, TokenInfo tokenInfo) {
        Long uid = tokenInfo.getUid();
        User user = userRepository.find(new UserId(uid));
        Student student = userFactory.getStudent(user);
        StudentId studentId = student.getStudentId();
        List<AssociationInfoVO> list =
                associationRepository.getInfoList(searchVO)
                        .stream()
                        .map(item -> {
                            AssociationInfoVO infoVO =
                                    AssociationAssembler.INSTANCE.convert2infoVO(item);
                            int status = AssociationStatus.NOT;
                            // 有该学生即已经加入
                            if (Objects.equals(item.getStudentId(), studentId.getId())) {
                                status = AssociationStatus.JOINED;
                            } else {
                                // 如果存在审核记录那么需要检查是否通过
                                AssociationAuditDO one = associationAuditRepository.getOne(studentId,
                                        new AssociationId(item.getId()));
                                if (Optional.ofNullable(one).isPresent()) {
                                    status = AssociationStatus.AUDIT;
                                }
                            }
                            infoVO.setStatus(status);
                            return infoVO;
                        })
                        .collect(Collectors.toList());
        return Result.ok(list);
    }
}
