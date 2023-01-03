package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.Post;

/**
 * 帖子持久化
 * Created in 2023/1/2 15:14
 *
 * @author Peter1303
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
