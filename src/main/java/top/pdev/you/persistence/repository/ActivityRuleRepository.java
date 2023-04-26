package top.pdev.you.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Activity;

import java.util.List;

/**
 * 活动规则仓库
 * Created in 2023/4/25 15:33
 *
 * @author Peter1303
 */
public interface ActivityRuleRepository extends IService<Activity.Rule> {
    List<Activity.Rule> findByActivity(Activity activity);

    int deleteByActivity(Activity activity);
}
