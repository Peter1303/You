package top.pdev.you.domain.service.activity;

import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.activity.AddActivityCommand;
import top.pdev.you.domain.command.activity.UpdateActivityCommand;
import top.pdev.you.domain.model.dto.ActivityInfoDTO;

import java.util.List;

/**
 * 活动服务
 * Created in 2023/4/24 20:31
 *
 * @author Peter1303
 */
public interface ActivityService {
    ActivityInfoDTO get(IdCommand command);

    List<ActivityInfoDTO> list();

    void add(AddActivityCommand command);

    void update(UpdateActivityCommand command);

    void delete(IdCommand command);

    void participate(IdCommand command);
}
