package top.pdev.you.domain.service.like.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Like;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.like.LikeService;
import top.pdev.you.infrastructure.factory.LikeFactory;
import top.pdev.you.persistence.repository.LikeRepository;
import top.pdev.you.persistence.repository.PostRepository;
import top.pdev.you.web.command.IdCommand;

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
    private PostRepository postRepository;

    @Resource
    private LikeFactory likeFactory;

    @Transactional
    @Override
    public void add(User user, IdCommand idCommand) {
        Long postId = idCommand.getId();
        // 检查是否已经点过赞了
        if (likeRepository.existsByUserIdAndPostId(user.getId(), postId)) {
            throw new BusinessException("已经点过赞了");
        }
        // 检查帖子是否存在
        if (!postRepository.existsById(postId)) {
            throw new BusinessException("帖子不存在");
        }
        Like like = likeFactory.newLike();
        like.setUserId(user.getId());
        like.setPostId(postId);
        if (!likeRepository.save(like)) {
            throw new BusinessException("点赞失败");
        }
    }

    @Transactional
    @Override
    public void delete(User user, IdCommand idCommand) {
        Like like = likeRepository.findByPostIdAndUserId(idCommand.getId(), user.getId());
        if (!likeRepository.removeById(like)) {
            throw new BusinessException("取消点赞失败");
        }
    }
}
