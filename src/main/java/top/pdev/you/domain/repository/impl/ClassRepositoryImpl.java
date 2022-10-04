package top.pdev.you.domain.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.domain.mapper.ClassMapper;
import top.pdev.you.domain.repository.ClassRepository;
import top.pdev.you.interfaces.model.dto.ClassInfoDTO;

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
    public List<ClassInfoDTO> getClassInfo(String name) {
        LambdaQueryWrapper<ClassDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ClassDO::getId, ClassDO::getName);
        if (StrUtil.isAllNotBlank(name)) {
            queryWrapper.like(ClassDO::getName, name);
        }
        return mapper.getClassInfoList(name);
    }
}
