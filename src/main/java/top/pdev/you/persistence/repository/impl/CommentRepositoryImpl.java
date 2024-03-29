package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.persistence.mapper.CommentMapper;
import top.pdev.you.persistence.repository.CommentRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 评论仓库实现类
 * Created in 2023/1/2 18:17
 *
 * @author Peter1303
 */
@Repository
public class CommentRepositoryImpl
        extends BaseRepository<CommentMapper, Comment>
        implements CommentRepository {
    @Resource
    private CommentMapper mapper;

    @Override
    public Comment findById(Long commentId) {
        Comment comment = mapper.selectById(commentId);
        if (!Optional.ofNullable(comment).isPresent()) {
            throw new BusinessException("找不到评论");
        }
        return comment;
    }

    @Override
    public List<Comment> findByPostIdOrderByTimeDesc(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getPostId, id)
                .orderByDesc(Comment::getTime);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<Comment> findCommentContainingOrderByTimeDesc(String content) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Comment::getComment, content);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Long countCommentByPostId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getPostId, id);
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public boolean deleteById(Long id) {
        return removeById(id);
    }
}
