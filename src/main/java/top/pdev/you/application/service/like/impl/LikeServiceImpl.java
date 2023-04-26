package top.pdev.you.application.service.like.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.like.LikeService;
import top.pdev.you.domain.entity.Like;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
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
        Post post = postRepository.findById(idCommand.getId());
        Like like = likeFactory.newLike();
        like.addLike(user, post);
    }

    @Transactional
    @Override
    public void delete(User user, IdCommand idCommand) {
        Like like = likeRepository.findByPostIdAndUserId(idCommand.getId(), user.getId());
        like.cancelLike();
    }
}
