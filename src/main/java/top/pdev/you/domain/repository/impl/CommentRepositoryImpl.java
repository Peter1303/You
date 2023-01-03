package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.entity.data.CommentDO;
import top.pdev.you.domain.entity.types.CommentId;
import top.pdev.you.domain.entity.types.PostId;
import top.pdev.you.domain.mapper.CommentMapper;
import top.pdev.you.domain.repository.CommentRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Comment findById(CommentId commentId) {
        checkId(commentId);
        CommentDO commentDO = mapper.selectById(commentId.getId());
        if (!Optional.ofNullable(commentDO).isPresent()) {
            throw new BusinessException("找不到评论");
        }
        return new Comment(commentDO);
    }

    @Override
    public List<Comment> findByPostId(PostId id) {
        checkId(id);
        LambdaQueryWrapper<CommentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentDO::getPostId, id.getId());
        List<CommentDO> list = mapper.selectList(queryWrapper);
        return list.stream()
                .map(Comment::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long countCommentByPostId(PostId id) {
        checkId(id);
        LambdaQueryWrapper<CommentDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentDO::getPostId, id.getId());
        return mapper.selectCount(queryWrapper);
    }
}
