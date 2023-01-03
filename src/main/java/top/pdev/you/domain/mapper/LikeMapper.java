package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.Like;

/**
 * 点赞持久化
 * Created in 2023/1/2 17:52
 *
 * @author Peter1303
 */
@Mapper
public interface LikeMapper extends BaseMapper<Like> {
}
