package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.domain.entity.Like;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.types.LikeId;
import top.pdev.you.domain.entity.types.PostId;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.factory.LikeFactory;
import top.pdev.you.domain.repository.LikeRepository;
import top.pdev.you.domain.repository.PostRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.LikeService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.IdVO;

import javax.annotation.Resource;

/**
 * 点赞服务实现类
 * Created in 2023/1/2 19:14
 *
 * @author Peter1303
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private LikeRepository likeRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private PostRepository postRepository;

    @Resource
    private LikeFactory likeFactory;

    @Override
    public Result<?> add(TokenInfo tokenInfo, IdVO idVO) {
        Long uid = tokenInfo.getUid();
        User user = userRepository.find(new UserId(uid));
        Post post = postRepository.findById(new PostId(idVO.getId()));
        Like like = likeFactory.newLike();
        like.addLike(user, post);
        return Result.ok();
    }

    @Override
    public Result<?> delete(IdVO idVO) {
        Like like = likeRepository.findById(new LikeId(idVO.getId()));
        like.cancelLike();
        return Result.ok();
    }
}
