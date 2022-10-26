package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.InstituteDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.domain.mapper.InstituteMapper;
import top.pdev.you.domain.repository.InstituteRepository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 学院仓库实现类
 * Created in 2022/10/26 11:38
 *
 * @author Peter1303
 */
@Repository
public class InstituteRepositoryImpl
        extends ServiceImpl<InstituteMapper, InstituteDO>
        implements InstituteRepository {
    @Resource
    private InstituteMapper mapper;

    @Override
    public String getName(Id id) {
        if (Optional.ofNullable(id).isPresent()) {
            LambdaQueryWrapper<InstituteDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InstituteDO::getId, id.getId());
            InstituteDO instituteDO = mapper.selectOne(queryWrapper);
            return instituteDO.getName();
        }
        return null;
    }
}
