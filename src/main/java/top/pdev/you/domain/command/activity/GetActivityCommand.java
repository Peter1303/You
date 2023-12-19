package top.pdev.you.domain.command.activity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 获取活动命令
 * Created in 2023/12/19 9:05
 *
 * @author Peter1303
 */
@Data
public class GetActivityCommand {
    @Length(max = 255)
    private String search;
}
