package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.Activity;

/**
 * 活动持久化
 * Created in 2023/4/24 20:49
 *
 * @author Peter1303
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
}
