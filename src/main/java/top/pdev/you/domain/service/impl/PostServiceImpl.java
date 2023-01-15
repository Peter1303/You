package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.exception.PermissionDeniedException;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.PostFactory;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.CommentRepository;
import top.pdev.you.domain.repository.PostRepository;
import top.pdev.you.domain.service.PermissionService;
import top.pdev.you.domain.service.PostService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.assembler.PostAssembler;
import top.pdev.you.interfaces.model.vo.PostInfoVO;
import top.pdev.you.interfaces.model.vo.req.ChangePostVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
import top.pdev.you.interfaces.model.vo.req.PostListVO;
import top.pdev.you.interfaces.model.vo.req.PostVO;

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
    private PostAssembler postAssembler;

    @Override
    public Result<?> post(User user, PostVO postVO) {
        // TODO implements topic
        Long topicId = postVO.getTopicId();

        Long associationId = postVO.getAssociationId();
        // 检查社团是否存在
        if (Optional.ofNullable(associationId).isPresent()) {
            associationRepository.findById(associationId);
        }

        Post post = postFactory.newPost();
        post.setContent(postVO.getContent());
        post.setUserId(user.getId());
        post.setAssociationId(associationId);
        post.save();
        return Result.ok();
    }

    @Override
    public Result<?> associationPost(User user, PostVO postVO) {
        return post(user, postVO);
    }

    @Override
    public Result<?> details(User user, IdVO idVO) {
        Post post = postRepository.findById(idVO.getId());
        PostInfoVO infoVO = postAssembler.convert(user, post);
        infoVO.setContent(post.getContent());
        return Result.ok(infoVO);
    }

    @Override
    public Result<?> list(User user, PostListVO postListVO) {
        Long id = postListVO.getId();
        String search = postListVO.getSearch();
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
        return Result.ok(postAssembler.convertToBriefList(posts, user));
    }

    @Override
    public Result<?> listOfUser(User user) {
        Long id = user.getId();
        List<Post> posts = postRepository.findByUserIdOrderByTimeDesc(id);
        return Result.ok(postAssembler.convertToBriefList(posts, user));
    }

    @Override
    public Result<?> delete(User user, IdVO idVO) {
        Post post = postRepository.findById(idVO.getId());
        if (!permissionService.editable(user, post.getUserId())) {
            throw new PermissionDeniedException();
        }
        post.delete();
        return Result.ok();
    }

    @Override
    public Result<?> changePost(ChangePostVO changePostVO) {
        Long id = changePostVO.getId();
        Post post = postRepository.findById(id);
        // TODO changeTopicId
        post.changeContent(changePostVO.getContent());
        return Result.ok();
    }
}
