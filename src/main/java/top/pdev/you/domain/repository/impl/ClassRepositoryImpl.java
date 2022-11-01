package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.mapper.ClassMapper;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.interfaces.model.dto.ClassInfoDTO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 班级仓库实现类
 * Created in 2022/10/3 18:20
 *
 * @author Peter1303
 */
@Repository
public class ClassRepositoryImpl
        extends ServiceImpl<ClassMapper, ClassDO>
        implements ClassRepository {
    @Resource
    private ClassMapper mapper;

    @Override
    public Clazz find(Long id) {
        LambdaQueryWrapper<ClassDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassDO::getId, id);
        ClassDO classDO = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(classDO).isPresent()) {
            return null;
        }
        // TODO cache
        return new Clazz(classDO);
    }

    @Override
    public ClassDO getDO(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<ClassInfoDTO> getClassInfo(SearchVO vo) {
        return mapper.getClassInfoList(vo);
    }

    @Override
    public String getName(Id id) {
        LambdaQueryWrapper<ClassDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassDO::getId, id.getId());
        ClassDO classDO = mapper.selectOne(queryWrapper);
        if (Optional.ofNullable(classDO).isPresent()) {
            return classDO.getName();
        }
        return null;
    }

    @Override
    public boolean exists(String name, Long instituteId, Long campusId) {
        LambdaQueryWrapper<ClassDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClassDO::getName, name)
                .eq(ClassDO::getInstituteId, instituteId)
                .eq(ClassDO::getCampusId, campusId);
        return mapper.exists(queryWrapper);
    }
}
