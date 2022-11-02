package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.CampusDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.mapper.CampusMapper;
import top.pdev.you.domain.repository.CampusRepository;
import top.pdev.you.interfaces.assembler.CampusAssembler;
import top.pdev.you.interfaces.model.dto.CampusInfoDTO;

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
        extends ServiceImpl<CampusMapper, CampusDO>
        implements CampusRepository {
    @Resource
    private CampusMapper mapper;

    @Override
    public String getName(Id id) {
        if (Optional.ofNullable(id).isPresent()) {
            LambdaQueryWrapper<CampusDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CampusDO::getId, id.getId());
            CampusDO campusDO = mapper.selectOne(queryWrapper);
            return campusDO.getName();
        }
        return null;
    }

    @Override
    public List<CampusInfoDTO> getCampusInfo(String name) {
        LambdaQueryWrapper<CampusDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(CampusDO::getName, name);
        List<CampusDO> list = mapper.selectList(queryWrapper);
        return list.stream()
                .map(CampusAssembler.INSTANCE::convert)
                .collect(Collectors.toList());
    }

    @Override
    public boolean exists(Long campusId) {
        LambdaQueryWrapper<CampusDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CampusDO::getId, campusId);
        return mapper.exists(queryWrapper);
    }

    @Override
    public boolean exists(String name) {
        LambdaQueryWrapper<CampusDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CampusDO::getName, name);
        return mapper.exists(queryWrapper);
    }
}
