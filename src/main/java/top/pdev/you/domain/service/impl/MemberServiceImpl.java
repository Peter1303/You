package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.UserFactory;
import top.pdev.you.domain.service.MemberService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.StudentInfoVO;
import top.pdev.you.interfaces.model.vo.UserInfoVO;

import javax.annotation.Resource;
import java.util.List;
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
    private UserFactory userFactory;

    @Override
    public Result<?> list(User user) {
        Manager manager = userFactory.getManager(user);
        Association association = manager.belongAssociation();
        List<Student> participants = association.participants();
        List<StudentInfoVO> list = participants.stream().map(student -> {
            StudentInfoVO infoVO = new StudentInfoVO();
            infoVO.setStudentId(student.getId());
            infoVO.setName(student.getName());
            infoVO.setClazz(student.getClazz());
            infoVO.setInstitute(student.getInstitute());
            infoVO.setCampus(student.getCampus());
            return infoVO;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }
}
