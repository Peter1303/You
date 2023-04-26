package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.ActivityParticipant;

/**
 * 活动参与
 * Created in 2023/4/25 20:06
 *
 * @author Peter1303
 */
@Mapper
public interface ActivityParticipantMapper extends BaseMapper<ActivityParticipant> {
}
