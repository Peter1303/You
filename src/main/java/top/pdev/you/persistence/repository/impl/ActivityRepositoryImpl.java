package top.pdev.you.persistence.repository.impl;

import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.Activity;
import top.pdev.you.persistence.mapper.ActivityMapper;
import top.pdev.you.persistence.repository.ActivityRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

/**
 * 活动仓库实现
 * Created in 2023/4/24 20:50
 *
 * @author Peter1303
 */
@Repository
public class ActivityRepositoryImpl
        extends BaseRepository<ActivityMapper, Activity>
        implements ActivityRepository {
}
