package top.pdev.you.web.activity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.activity.AddActivityCommand;
import top.pdev.you.domain.command.activity.UpdateActivityCommand;
import top.pdev.you.domain.model.dto.ActivityInfoDTO;
import top.pdev.you.domain.service.activity.ActivityService;
import top.pdev.you.infrastructure.result.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动控制器
 * Created in 2023/4/24 17:27
 *
 * @author Peter1303
 */
@RequestMapping("/activity")
@RestController
public class ActivityController {
    @Resource
    private ActivityService activityService;

    @PostMapping("")
    public Result<?> add(@RequestBody @Validated AddActivityCommand command) {
        activityService.add(command);
        return Result.ok();
    }

    @PutMapping("")
    public Result<?> update(@RequestBody @Validated UpdateActivityCommand command) {
        activityService.update(command);
        return Result.ok();
    }

    @DeleteMapping("")
    public Result<?> delete(@RequestBody @Validated IdCommand command) {
        activityService.delete(command);
        return Result.ok();
    }

    @GetMapping("")
    public Result<?> get(@Validated IdCommand command) {
        ActivityInfoDTO infoDTO = activityService.get(command);
        return Result.ok(infoDTO);
    }

    @GetMapping("list")
    public Result<?> list() {
        List<ActivityInfoDTO> list = activityService.list();
        return Result.ok(list);
    }

    @PostMapping("participate")
    public Result<?> participate(@RequestBody @Validated IdCommand command) {
        activityService.participate(command);
        return Result.ok();
    }
}
