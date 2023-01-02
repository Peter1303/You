package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.data.CommentDO;

/**
 * 评论持久化
 * Created in 2023/1/2 18:15
 *
 * @author Peter1303
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentDO> {
}
