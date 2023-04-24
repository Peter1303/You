package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pdev.you.domain.entity.Post;

import java.util.List;

/**
 * 帖子持久化
 * Created in 2023/1/2 15:14
 *
 * @author Peter1303
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    List<Post> findByAssociationIdOrContentContainingOrderByTimeDesc(@Param("aid") Long associationId,
                                                                     @Param("content") String content);
}
