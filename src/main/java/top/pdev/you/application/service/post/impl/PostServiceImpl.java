package top.pdev.you.application.service.post.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.post.PostService;
import top.pdev.you.common.exception.PermissionDeniedException;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.factory.PostFactory;
import top.pdev.you.persistence.repository.AssociationRepository;
import top.pdev.you.persistence.repository.CommentRepository;
import top.pdev.you.persistence.repository.PostRepository;
import top.pdev.you.application.service.permission.PermissionService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.infrastructure.mapper.PostMapper;
import top.pdev.you.domain.ui.vm.PostInfoResponse;
import top.pdev.you.web.post.command.ChangePostCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.post.command.PostListCommand;
import top.pdev.you.web.post.command.PostCommand;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 帖子服务实现类
 * Created in 2023/1/2 13:35
 *
 * @author Peter1303
 */
@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PermissionService permissionService;

    @Resource
    private PostRepository postRepository;

    @Resource
    private AssociationRepository associationRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private PostFactory postFactory;

    @Resource
    private PostMapper postMapper;

    @Transactional
    @Override
    public Result<?> post(User user, PostCommand postCommand) {
        // TODO implements topic
        Long topicId = postCommand.getTopicId();

        Long associationId = postCommand.getAssociationId();
        // 检查社团是否存在
        if (Optional.ofNullable(associationId).isPresent()) {
            associationRepository.findById(associationId);
        }

        Post post = postFactory.newPost();
        post.setContent(postCommand.getContent());
        post.setUserId(user.getId());
        post.setAssociationId(associationId);
        post.save();
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> associationPost(User user, PostCommand postCommand) {
        return post(user, postCommand);
    }

    @Override
    public Result<?> details(User user, IdCommand idCommand) {
        Post post = postRepository.findById(idCommand.getId());
        PostInfoResponse infoVO = postMapper.convert(user, post);
        infoVO.setContent(post.getContent());
        return Result.ok(infoVO);
    }

    @Override
    public Result<?> list(User user, PostListCommand postListCommand) {
        Long id = postListCommand.getId();
        String search = postListCommand.getSearch();
        List<Post> posts =
                postRepository.findByAssociationIdOrContentContainingOrderByTimeDesc(id, search);
        if (Optional.ofNullable(search).isPresent()) {
            List<Comment> comments = commentRepository.findCommentContainingOrderByTimeDesc(search);
            List<Long> idList = comments.stream()
                    .map(Comment::getPostId)
                    .collect(Collectors.toList());
            List<Post> postList = idList.stream()
                    .map(postId -> postRepository.findById(postId))
                    .collect(Collectors.toList());
            posts.addAll(postList);
        }
        return Result.ok(postMapper.convertToBriefList(posts, user));
    }

    @Override
    public Result<?> listOfUser(User user) {
        Long id = user.getId();
        List<Post> posts = postRepository.findByUserIdOrderByTimeDesc(id);
        return Result.ok(postMapper.convertToBriefList(posts, user));
    }

    @Transactional
    @Override
    public Result<?> delete(User user, IdCommand idCommand) {
        Post post = postRepository.findById(idCommand.getId());
        if (!permissionService.editable(user, post.getUserId())) {
            throw new PermissionDeniedException();
        }
        post.delete();
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> changePost(ChangePostCommand changePostVO) {
        Long id = changePostVO.getId();
        Post post = postRepository.findById(id);
        // TODO changeTopicId
        post.changeContent(changePostVO.getContent());
        return Result.ok();
    }
}
