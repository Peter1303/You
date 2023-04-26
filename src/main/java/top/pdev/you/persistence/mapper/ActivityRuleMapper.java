package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.Activity;

/**
 * 活动规则
 * Created in 2023/4/25 15:32
 *
 * @author Peter1303
 */
@Mapper
public interface ActivityRuleMapper extends BaseMapper<Activity.Rule> {
}
