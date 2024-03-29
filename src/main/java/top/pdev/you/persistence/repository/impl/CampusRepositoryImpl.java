package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.domain.model.dto.CampusInfoDTO;
import top.pdev.you.infrastructure.mapper.CampusMapper;
import top.pdev.you.persistence.repository.CampusRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 校区仓库实现类
 * Created in 2022/10/26 11:13
 *
 * @author Peter1303
 */
@Repository
public class CampusRepositoryImpl
        extends ServiceImpl<top.pdev.you.persistence.mapper.CampusMapper, Campus>
        implements CampusRepository {
    @Resource
    private top.pdev.you.persistence.mapper.CampusMapper mapper;

    @Override
    public Campus findById(Long id) {
        Campus campus = mapper.selectById(id);
        if (!Optional.ofNullable(id).isPresent()) {
            throw new BusinessException("找不到校区");
        }
        return campus;
    }

    @Override
    public List<CampusInfoDTO> getCampusInfo(String name) {
        LambdaQueryWrapper<Campus> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Campus::getName, name);
        List<Campus> list = mapper.selectList(queryWrapper);
        return list.stream()
                .map(CampusMapper.INSTANCE::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long campusId) {
        LambdaQueryWrapper<Campus> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Campus::getId, campusId);
        return mapper.exists(queryWrapper);
    }

    @Override
    public boolean existsByName(String name) {
        LambdaQueryWrapper<Campus> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Campus::getName, name);
        return mapper.exists(queryWrapper);
    }
}
