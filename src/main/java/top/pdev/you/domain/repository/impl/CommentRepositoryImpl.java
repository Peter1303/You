package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.CommentDO;
import top.pdev.you.domain.entity.types.PostId;
import top.pdev.you.domain.mapper.CommentMapper;
import top.pdev.you.domain.repository.CommentRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;

/**
 * 评论仓库实现类
 * Created in 2023/1/2 18:17
 *
 * @author Peter1303
 */
@Repository
public class CommentRepositoryImpl
        extends BaseRepository<CommentMapper, CommentDO>
        implements CommentRepository {
    @Resource
    private CommentMapper mapper;

    @Override
    public Long countCommentByPostId(PostId id) {
        checkId(id);
        LambdaQueryWrapper<CommentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentDO::getPostId, id.getId());
        return mapper.selectCount(queryWrapper);
    }
}
