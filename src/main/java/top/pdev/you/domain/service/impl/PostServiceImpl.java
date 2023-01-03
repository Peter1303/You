package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.PostFactory;
import top.pdev.you.domain.repository.AssociationRepository;
import top.pdev.you.domain.repository.CommentRepository;
import top.pdev.you.domain.repository.LikeRepository;
import top.pdev.you.domain.repository.PostRepository;
import top.pdev.you.domain.repository.UserRepository;
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
    private PostRepository postRepository;

    @Resource
    private AssociationRepository associationRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private LikeRepository likeRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private PostFactory postFactory;

    @Override
    public Result<?> post(TokenInfo tokenInfo, PostVO postVO) {
        // TODO implements topic
        Long topicId = postVO.getTopicId();

        Long associationId = postVO.getAssociationId();
        // 检查社团是否存在
        if (Optional.ofNullable(associationId).isPresent()) {
            associationRepository.findById(associationId);
        }

        Post post = postFactory.newPost();
        post.setContent(postVO.getContent());
        post.setUserId(tokenInfo.getUid());
        post.setAssociationId(associationId);
        post.save();
        return Result.ok();
    }

    @Override
    public Result<?> associationPost(TokenInfo tokenInfo, PostVO postVO) {
        return post(tokenInfo, postVO);
    }

    @Override
    public Result<?> details(IdVO idVO) {
        Post post = postRepository.findById(idVO.getId());
        PostInfoVO infoVO = convert(post);
        infoVO.setContent(post.getContent());
        return Result.ok(infoVO);
    }

    @Override
    public Result<?> list(PostListVO postListVO) {
        Long id = postListVO.getId();
        List<Post> posts = postRepository.findByAssociationId(id);
        List<PostInfoVO> list = posts.stream().map(post -> {
            PostInfoVO infoVO = convert(post);
            infoVO.setSummary(post.getContent().substring(0, 40) + "...");
            return infoVO;
        }).collect(Collectors.toList());
        return Result.ok(list);
    }

    @Override
    public Result<?> delete(TokenInfo tokenInfo, IdVO idVO) {
        Post post = postRepository.findById(idVO.getId());
        Long uid = tokenInfo.getUid();
        User user = userRepository.findById(uid);
        post.delete(user);
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

    /**
     * 转换
     *
     * @param post 帖子
     * @return {@link PostInfoVO}
     */
    private PostInfoVO convert(Post post) {
        PostInfoVO infoVO = PostAssembler.INSTANCE.convert(post);
        Long userId = post.getUserId();
        User user = userRepository.findById(userId);
        RoleEntity role = user.getRoleDomain();
        String name = role.getName();
        infoVO.setName(name);
        Long postId = post.getId();
        infoVO.setLikes(likeRepository.countLikesByPostId(postId));
        infoVO.setComments(commentRepository.countCommentByPostId(postId));
        infoVO.setLiked(likeRepository.existsByUserIdAndPostId(user.getId(), postId));
        return infoVO;
    }
}
