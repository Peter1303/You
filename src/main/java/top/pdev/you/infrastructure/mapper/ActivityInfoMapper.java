package top.pdev.you.infrastructure.mapper;

import top.pdev.you.domain.entity.Activity;
import top.pdev.you.domain.model.dto.ActivityInfoDTO;

import java.util.List;

/**
 * 活动信息映射器
 * Created in 2023/4/25 20:33
 *
 * @author Peter1303
 */
public interface ActivityInfoMapper {
    ActivityInfoDTO convert(Activity activity);

    ActivityInfoDTO convert(Activity activity, List<Activity.Rule> rules);
}
