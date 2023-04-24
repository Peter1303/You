package top.pdev.you.persistence.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Like;
import top.pdev.you.persistence.mapper.LikeMapper;
import top.pdev.you.persistence.repository.LikeRepository;
import top.pdev.you.persistence.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 点赞仓库实现类
 * Created in 2023/1/2 17:58
 *
 * @author Peter1303
 */
@Repository
public class LikeRepositoryImpl
        extends BaseRepository<LikeMapper, Like>
        implements LikeRepository {
    @Resource
    private LikeMapper mapper;

    @Override
    public Like findById(Long likeId) {
        Like like = getById(likeId);
        if (!Optional.ofNullable(like).isPresent()) {
            throw new BusinessException("找不到点赞");
        }
        return like;
    }

    @Override
    public Like findByPostIdAndUserId(Long postId, Long userId) {
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getPostId, postId)
                .eq(Like::getUserId, userId);
        Like like = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(like).isPresent()) {
            throw new BusinessException("找不到点赞");
        }
        return like;
    }

    @Override
    public Long countLikesByPostId(Long id) {
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getPostId, id);
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public Boolean existsByUserIdAndPostId(Long userId, Long postId) {
        LambdaQueryWrapper<Like> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Like::getPostId, postId)
                .eq(Like::getUserId, userId);
        return mapper.exists(queryWrapper);
    }

    @Override
    public boolean deleteById(Long id) {
        return removeById(id);
    }
}
