package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.Clazz;
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
        extends ServiceImpl<ClassMapper, Clazz>
        implements ClassRepository {
    @Resource
    private ClassMapper mapper;

    @Override
    public Clazz findById(Long id) {
        LambdaQueryWrapper<Clazz> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Clazz::getId, id);
        Clazz clazz = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(clazz).isPresent()) {
            return null;
        }
        // TODO cache
        return clazz;
    }

    @Override
    public List<ClassInfoDTO> getClassInfo(SearchVO searchVO) {
        return mapper.getClassInfoList(searchVO);
    }

    @Override
    public boolean existsByNameAndInstituteIdAndCampusId(String name, Long instituteId, Long campusId) {
        LambdaQueryWrapper<Clazz> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Clazz::getName, name)
                .eq(Clazz::getInstituteId, instituteId)
                .eq(Clazz::getCampusId, campusId);
        return mapper.exists(queryWrapper);
    }
}
