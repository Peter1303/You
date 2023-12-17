package top.pdev.you.domain.service.teacher.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.service.teacher.TeacherService;
import top.pdev.you.persistence.repository.AssociationRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 老师服务实现类
 * Created in 2023/12/16 11:15
 *
 * @author Peter1303
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private AssociationRepository associationRepository;

    @Override
    public List<Association> getManagedAssociationList(Teacher teacher) {
        return associationRepository.getManagedList(teacher);
    }
}
