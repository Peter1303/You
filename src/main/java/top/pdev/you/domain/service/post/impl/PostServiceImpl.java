package top.pdev.you.domain.service.post.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.PermissionDeniedException;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.post.ChangePostCommand;
import top.pdev.you.domain.command.post.PostCommand;
import top.pdev.you.domain.command.post.PostListCommand;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.model.vm.PostInfoResponse;
import top.pdev.you.domain.service.permission.PermissionService;
import top.pdev.you.domain.service.post.PostService;
import top.pdev.you.infrastructure.factory.PostFactory;
import top.pdev.you.infrastructure.mapper.PostInfoMapper;
import top.pdev.you.persistence.repository.AssociationRepository;
import top.pdev.you.persistence.repository.CommentRepository;
import top.pdev.you.persistence.repository.PostRepository;

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
    private PostInfoMapper postInfoMapper;

    @Transactional
    @Override
    public void post(User user, PostCommand postCommand) {
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
        if (!postRepository.save(post)) {
            throw new BusinessException("无法发布帖子");
        }
    }

    @Transactional
    @Override
    public void associationPost(User user, PostCommand postCommand) {
        post(user, postCommand);
    }

    @Override
    public PostInfoResponse details(User user, IdCommand idCommand) {
        Post post = postRepository.findById(idCommand.getId());
        PostInfoResponse infoVO = postInfoMapper.convert(user, post);
        infoVO.setContent(post.getContent());
        return infoVO;
    }

    @Override
    public List<PostInfoResponse> list(User user, PostListCommand postListCommand) {
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
        return postInfoMapper.convertToBriefList(posts, user);
    }

    @Override
    public List<PostInfoResponse> listOfUser(User user) {
        Long id = user.getId();
        List<Post> posts = postRepository.findByUserIdOrderByTimeDesc(id);
        return postInfoMapper.convertToBriefList(posts, user);
    }

    @Transactional
    @Override
    public void delete(User user, IdCommand idCommand) {
        Post post = postRepository.findById(idCommand.getId());
        if (!permissionService.editable(user, post.getUserId())) {
            throw new PermissionDeniedException();
        }
        if (!postRepository.deleteById(post.getId())) {
            throw new BusinessException("无法删除帖子");
        }
    }

    @Transactional
    @Override
    public void changePost(ChangePostCommand changePostVO) {
        Long id = changePostVO.getId();
        Post post = postRepository.findById(id);
        post.setId(id);
        post.setContent(changePostVO.getContent());
        if (!postRepository.saveOrUpdate(post)) {
            throw new BusinessException("无法更改内容");
        }
    }
}
