package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.domain.factory.ClassFactory;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.domain.service.ClassService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.dto.ClassInfoDTO;
import top.pdev.you.interfaces.model.vo.ListVO;
import top.pdev.you.interfaces.model.vo.req.AddClassVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

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
    public Result<?> getClassList(SearchVO vo) {
        List<ClassInfoDTO> list = classRepository.getClassInfo(vo);
        ListVO<ClassInfoDTO> listVO = new ListVO<>();
        listVO.setList(list);
        return Result.ok().setData(listVO);
    }

    @Override
    public Result<?> add(AddClassVO addClassVO) {
        Clazz clazz = classFactory.newClazz();
        ClassDO classDO = new ClassDO();
        classDO.setInstituteId(addClassVO.getInstituteId());
        classDO.setCampusId(addClassVO.getCampusId());
        classDO.setName(addClassVO.getName());
        classDO.setYear(addClassVO.getGrade());
        clazz.save(classDO);
        return Result.ok();
    }

    @Override
    public Result<?> delete(IdVO idVO) {
        if (!classRepository.removeById(idVO.getId())) {
            throw new BusinessException("删除班级失败");
        }
        return Result.ok();
    }
}
