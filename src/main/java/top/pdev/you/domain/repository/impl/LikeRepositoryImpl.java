package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Like;
import top.pdev.you.domain.entity.data.LikeDO;
import top.pdev.you.domain.entity.types.LikeId;
import top.pdev.you.domain.entity.types.PostId;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.mapper.LikeMapper;
import top.pdev.you.domain.repository.LikeRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

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
        extends BaseRepository<LikeMapper, LikeDO>
        implements LikeRepository {
    @Resource
    private LikeMapper mapper;

    @Override
    public Like findById(LikeId likeId) {
        checkId(likeId);
        LikeDO likeDO = getById(likeId.getId());
        if (!Optional.ofNullable(likeDO).isPresent()) {
            throw new BusinessException("找不到点赞");
        }
        return new Like(likeDO);
    }

    @Override
    public Long countLikesByPostId(PostId id) {
        checkId(id);
        LambdaQueryWrapper<LikeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikeDO::getPostId, id.getId());
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public Boolean liked(UserId userId, PostId postId) {
        checkId(userId);
        checkId(postId);
        LambdaQueryWrapper<LikeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikeDO::getPostId, postId.getId())
                .eq(LikeDO::getUid, userId.getId());
        return mapper.exists(queryWrapper);
    }
}
