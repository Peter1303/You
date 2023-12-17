package top.pdev.you.application.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import top.pdev.you.domain.entity.Activity;

import java.util.List;

/**
 * 活动事件
 * Created in 2023/12/17 14:11
 *
 * @author Peter1303
 */
@Getter
@Setter
public class ActivityEvent extends ApplicationEvent {
    private Activity activity;
    private List<Activity.Rule> rules;

    public ActivityEvent(Object source) {
        super(source);
    }
}
