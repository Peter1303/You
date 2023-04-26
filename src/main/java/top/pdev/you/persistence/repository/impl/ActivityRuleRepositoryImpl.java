package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.Activity;
import top.pdev.you.persistence.mapper.ActivityRuleMapper;
import top.pdev.you.persistence.repository.ActivityRuleRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动规则仓库实现
 * Created in 2023/4/25 15:33
 *
 * @author Peter1303
 */
@Repository
public class ActivityRuleRepositoryImpl
        extends BaseRepository<ActivityRuleMapper, Activity.Rule>
        implements ActivityRuleRepository {
    @Resource
    private ActivityRuleMapper mapper;

    @Override
    public List<Activity.Rule> findByActivity(Activity activity) {
        Long id = activity.getId();
        LambdaQueryWrapper<Activity.Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Activity.Rule::getActivityId, id);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public int deleteByActivity(Activity activity) {
        LambdaQueryWrapper<Activity.Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Activity.Rule::getActivityId, activity.getId());
        return mapper.delete(queryWrapper);
    }
}
