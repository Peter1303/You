package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.data.PostDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.PostId;
import top.pdev.you.domain.factory.PostFactory;
import top.pdev.you.domain.mapper.PostMapper;
import top.pdev.you.domain.repository.PostRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 帖子仓库实现类
 * Created in 2023/1/2 15:16
 *
 * @author Peter1303
 */
@Repository
public class PostRepositoryImpl
        extends BaseRepository<PostMapper, PostDO>
        implements PostRepository {
    @Resource
    private PostMapper mapper;

    @Resource
    private PostFactory postFactory;

    @Override
    public Post findById(PostId id) {
        checkId(id);
        PostDO postDO = getById(id.getId());
        Optional.ofNullable(postDO)
                .orElseThrow(() -> new BusinessException("找不到帖子"));
        return postFactory.getPost(postDO);
    }

    @Override
    public Boolean exists(PostId id) {
        checkId(id);
        LambdaQueryWrapper<PostDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PostDO::getId, id.getId());
        return mapper.exists(queryWrapper);
    }

    @Override
    public List<Post> getList(AssociationId associationId) {
        LambdaQueryWrapper<PostDO> queryWrapper = new LambdaQueryWrapper<>();
        if (Optional.ofNullable(associationId.getId()).isPresent()) {
            queryWrapper.eq(PostDO::getAssociationId, associationId.getId());
        }
        List<PostDO> list = mapper.selectList(queryWrapper);
        return list.stream()
                .map(postDO -> postFactory.getPost(postDO))
                .collect(Collectors.toList());
    }
}
