package top.pdev.you.application.service.activity;

import top.pdev.you.domain.ui.dto.ActivityInfoDTO;
import top.pdev.you.web.activity.command.AddActivityCommand;
import top.pdev.you.web.activity.command.UpdateActivityCommand;
import top.pdev.you.web.command.IdCommand;

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
