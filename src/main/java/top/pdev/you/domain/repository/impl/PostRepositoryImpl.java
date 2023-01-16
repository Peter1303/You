package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.mapper.PostMapper;
import top.pdev.you.domain.repository.PostRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 帖子仓库实现类
 * Created in 2023/1/2 15:16
 *
 * @author Peter1303
 */
@Repository
public class PostRepositoryImpl
        extends BaseRepository<PostMapper, Post>
        implements PostRepository {
    @Resource
    private PostMapper mapper;

    @Override
    public Post findById(Long id) {
        Post post = getById(id);
        Optional.ofNullable(post)
                .orElseThrow(() -> new BusinessException("找不到帖子"));
        return post;
    }

    @Override
    public Boolean existsById(Long id) {
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getId, id);
        return mapper.exists(queryWrapper);
    }

    @Override
    public List<Post> findByAssociationIdOrContentContainingOrderByTimeDesc(Long associationId,
                                                                            String content) {
        return mapper.findByAssociationIdOrContentContainingOrderByTimeDesc(associationId, content);
    }

    @Override
    public List<Post> findByUserIdOrderByTimeDesc(Long userId) {
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Post::getUserId, userId)
                .orderByDesc(Post::getTime);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }
}
