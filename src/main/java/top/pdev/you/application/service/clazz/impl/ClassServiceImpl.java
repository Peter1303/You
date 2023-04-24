package top.pdev.you.application.service.clazz.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.clazz.ClassService;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.infrastructure.factory.ClassFactory;
import top.pdev.you.persistence.repository.ClassRepository;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.domain.ui.dto.ClassInfoDTO;
import top.pdev.you.domain.ui.vm.ListResponse;
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
    private ClassFactory classFactory;

    @Override
    public Result<?> getClassList(SearchCommand vo) {
        List<ClassInfoDTO> list = classRepository.getClassInfo(vo);
        ListResponse<ClassInfoDTO> listResponse = new ListResponse<>();
        listResponse.setList(list);
        return Result.ok(listResponse);
    }

    @Transactional
    @Override
    public Result<?> add(AddClassCommand addClassCommand) {
        Clazz clazz = classFactory.newClazz();
        clazz.setInstituteId(addClassCommand.getInstituteId());
        clazz.setCampusId(addClassCommand.getCampusId());
        clazz.setName(addClassCommand.getName());
        clazz.setYear(addClassCommand.getGrade());
        clazz.save();
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> delete(IdCommand idCommand) {
        if (!classRepository.removeById(idCommand.getId())) {
            throw new BusinessException("删除班级失败");
        }
        return Result.ok();
    }
}
