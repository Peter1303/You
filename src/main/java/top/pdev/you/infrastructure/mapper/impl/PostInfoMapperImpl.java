package top.pdev.you.infrastructure.mapper.impl;

import org.springframework.stereotype.Component;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.persistence.repository.CommentRepository;
import top.pdev.you.persistence.repository.LikeRepository;
import top.pdev.you.persistence.repository.UserRepository;
import top.pdev.you.application.service.permission.PermissionService;
import top.pdev.you.infrastructure.mapper.PostInfoMapper;
import top.pdev.you.domain.ui.vm.PostInfoResponse;

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
public class PostInfoMapperImpl implements PostInfoMapper {
    @Resource
    private UserRepository userRepository;

    @Resource
    private LikeRepository likeRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private PermissionService permissionService;

    @Override
    public PostInfoResponse convert(Post post) {
        PostInfoResponse vo = new PostInfoResponse();
        vo.setId(post.getId());
        vo.setTime(post.getTime());
        return vo;
    }

    public PostInfoResponse convert(User currentUser, Post post) {
        PostInfoResponse infoVO = convert(post);
        Long userId = post.getUserId();
        User user = userRepository.findById(userId);
        RoleEntity role = user.getRoleDomain();
        String name = role.getName();
        infoVO.setName(name);
        Long postId = post.getId();
        infoVO.setLikes(likeRepository.countLikesByPostId(postId));
        infoVO.setComments(commentRepository.countCommentByPostId(postId));
        infoVO.setLiked(likeRepository.existsByUserIdAndPostId(currentUser.getId(), postId));
        infoVO.setDeletable(permissionService.editable(currentUser, userId));
        return infoVO;
    }

    @Override
    public List<PostInfoResponse> convertToBriefList(List<Post> posts, User user) {
        return posts.stream().map(post -> {
            PostInfoResponse infoVO = convert(user, post);
            String content = post.getContent();
            infoVO.setContent(null);
            if (content.length() > 70) {
                infoVO.setSummary(content.substring(0, 70) + "...");
            } else {
                infoVO.setSummary(content);
            }
            return infoVO;
        }).collect(Collectors.toList());
    }
}
