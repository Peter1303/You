package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.mapper.InstituteMapper;
import top.pdev.you.domain.repository.InstituteRepository;
import top.pdev.you.infrastructure.assembler.InstituteAssembler;
import top.pdev.you.domain.ui.dto.InstituteInfoDTO;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 学院仓库实现类
 * Created in 2022/10/26 11:38
 *
 * @author Peter1303
 */
@Repository
public class InstituteRepositoryImpl
        extends ServiceImpl<InstituteMapper, Institute>
        implements InstituteRepository {
    @Resource
    private InstituteMapper mapper;

    @Override
    public Institute findById(Long instituteId) {
        Institute institute = mapper.selectById(instituteId);
        if (!Optional.ofNullable(institute).isPresent()) {
            throw new BusinessException("找不到学院");
        }
        return institute;
    }

    @Override
    public List<InstituteInfoDTO> getInstituteInfo(String name) {
        LambdaQueryWrapper<Institute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Institute::getName, name);
        List<Institute> list = mapper.selectList(queryWrapper);
        return list.stream()
                .map(InstituteAssembler.INSTANCE::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long instituteId) {
        LambdaQueryWrapper<Institute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Institute::getId, instituteId);
        return mapper.exists(queryWrapper);
    }

    @Override
    public boolean existsByName(String name) {
        LambdaQueryWrapper<Institute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Institute::getName, name);
        return mapper.exists(queryWrapper);
    }
}
