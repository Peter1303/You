package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.Campus;

/**
 * 校区持久化
 * Created in 2022/10/26 11:12
 *
 * @author Peter1303
 */
@Mapper
public interface CampusMapper extends BaseMapper<Campus> {
}
