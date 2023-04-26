package top.pdev.you.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Activity;
import top.pdev.you.domain.entity.ActivityParticipant;
import top.pdev.you.domain.entity.User;

/**
 * 活动参与仓库
 * Created in 2023/4/25 20:09
 *
 * @author Peter1303
 */
public interface ActivityParticipantRepository extends IService<ActivityParticipant> {
    long countByActivity(Activity activity);

    boolean existsByActivityAndUser(Activity activity, User user);
}
