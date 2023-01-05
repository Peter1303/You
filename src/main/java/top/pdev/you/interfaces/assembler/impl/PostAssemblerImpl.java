package top.pdev.you.interfaces.assembler.impl;

import org.springframework.stereotype.Component;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.repository.CommentRepository;
import top.pdev.you.domain.repository.LikeRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.PermissionService;
import top.pdev.you.interfaces.assembler.PostAssembler;
import top.pdev.you.interfaces.model.vo.PostInfoVO;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 帖子封装器实现类
 * Created in 2023/1/4 17:01
 *
 * @author Peter1303
 */
@Component
public class PostAssemblerImpl implements PostAssembler {
    @Resource
    private UserRepository userRepository;

    @Resource
    private LikeRepository likeRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private PermissionService permissionService;

    @Override
    public PostInfoVO convert(Post post) {
        PostInfoVO vo = new PostInfoVO();
        vo.setId(post.getId());
        vo.setTime(post.getTime());
        return vo;
    }

    public PostInfoVO convert(User currentUser, Post post) {
        PostInfoVO infoVO = convert(post);
        Long userId = post.getUserId();
        User user = userRepository.findById(userId);
        RoleEntity role = user.getRoleDomain();
        String name = role.getName();
        infoVO.setName(name);
        Long postId = post.getId();
        infoVO.setLikes(likeRepository.countLikesByPostId(postId));
        infoVO.setComments(commentRepository.countCommentByPostId(postId));
        infoVO.setLiked(likeRepository.existsByUserIdAndPostId(user.getId(), postId));
        infoVO.setDeletable(permissionService.editable(currentUser, userId));
        return infoVO;
    }

    @Override
    public List<PostInfoVO> convertToBriefList(List<Post> posts, User user) {
        return posts.stream().map(post -> {
            PostInfoVO infoVO = convert(user, post);
            String content = post.getContent();
            infoVO.setContent(null);
            if (content.length() > 40) {
                infoVO.setSummary(content.substring(0, 40) + "...");
            } else {
                infoVO.setSummary(content);
            }
            return infoVO;
        }).collect(Collectors.toList());
    }
}
