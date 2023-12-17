package top.pdev.you.domain.service.clazz.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.domain.service.clazz.ClassService;
import top.pdev.you.domain.model.dto.ClassInfoDTO;
import top.pdev.you.domain.model.vm.ListResponse;
import top.pdev.you.infrastructure.factory.ClassFactory;
import top.pdev.you.persistence.repository.CampusRepository;
import top.pdev.you.persistence.repository.ClassRepository;
import top.pdev.you.persistence.repository.InstituteRepository;
import top.pdev.you.web.clazz.command.AddClassCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.query.command.SearchCommand;

import javax.annotation.Resource;
import java.util.List;

/**
 * 班级服务实现类
 * Created in 2022/10/3 18:28
 *
 * @author Peter1303
 */
@Service
public class ClassServiceImpl implements ClassService {
    @Resource
    private ClassRepository classRepository;

    @Resource
    private CampusRepository campusRepository;

    @Resource
    private InstituteRepository instituteRepository;

    @Resource
    private ClassFactory classFactory;

    @Override
    public ListResponse<ClassInfoDTO> getClassList(SearchCommand vo) {
        List<ClassInfoDTO> list = classRepository.getClassInfo(vo);
        ListResponse<ClassInfoDTO> listResponse = new ListResponse<>();
        listResponse.setList(list);
        return listResponse;
    }

    @Transactional
    @Override
    public void add(AddClassCommand addClassCommand) {
        Clazz clazz = classFactory.newClazz();
        Long campusId = addClassCommand.getCampusId();
        Long instituteId = addClassCommand.getInstituteId();
        String name = addClassCommand.getName();
        Integer grade = addClassCommand.getGrade();
        clazz.setInstituteId(instituteId);
        clazz.setCampusId(campusId);
        clazz.setName(name);
        clazz.setYear(grade);
        if (!campusRepository.existsById(campusId)) {
            throw new BusinessException("没有找到该校区");
        }
        if (!instituteRepository.existsById(instituteId)) {
            throw new BusinessException("没有找到该学院");
        }
        // 检查是否重复
        if (classRepository.existsByNameAndInstituteIdAndCampusId(
                name,
                instituteId,
                campusId)) {
            throw new BusinessException("已经存在相同的班级");
        }
        classRepository.save(clazz);
    }

    @Transactional
    @Override
    public void delete(IdCommand idCommand) {
        if (!classRepository.removeById(idCommand.getId())) {
            throw new BusinessException("删除班级失败");
        }
    }
}
