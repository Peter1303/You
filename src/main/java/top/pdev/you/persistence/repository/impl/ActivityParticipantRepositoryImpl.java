package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.Activity;
import top.pdev.you.domain.entity.ActivityParticipant;
import top.pdev.you.domain.entity.User;
import top.pdev.you.persistence.mapper.ActivityParticipantMapper;
import top.pdev.you.persistence.repository.ActivityParticipantRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

import javax.annotation.Resource;

/**
 * 活动参与者仓库实现
 * Created in 2023/4/25 20:09
 *
 * @author Peter1303
 */
@Repository
public class ActivityParticipantRepositoryImpl
        extends BaseRepository<ActivityParticipantMapper, ActivityParticipant>
        implements ActivityParticipantRepository {
    @Resource
    private ActivityParticipantMapper mapper;

    @Override
    public long countByActivity(Activity activity) {
        LambdaQueryWrapper<ActivityParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityParticipant::getActivityId, activity.getId());
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public boolean existsByActivityAndUser(Activity activity, User user) {
        LambdaQueryWrapper<ActivityParticipant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityParticipant::getActivityId, activity.getId())
                .eq(ActivityParticipant::getUserId, user.getId());
        return mapper.exists(queryWrapper);
    }
}
