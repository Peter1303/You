package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.CampusDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.mapper.CampusMapper;
import top.pdev.you.domain.repository.CampusRepository;

import javax.annotation.Resource;
import java.util.Optional;

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
    public boolean exists(Long campusId) {
        LambdaQueryWrapper<CampusDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CampusDO::getId, campusId);
        return mapper.exists(queryWrapper);
    }
}
