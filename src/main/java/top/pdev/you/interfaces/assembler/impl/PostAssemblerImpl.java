package top.pdev.you.interfaces.assembler.impl;

import org.springframework.stereotype.Component;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.repository.CommentRepository;
import top.pdev.you.domain.repository.LikeRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.AdminService;
import top.pdev.you.interfaces.assembler.PostAssembler;
import top.pdev.you.interfaces.model.vo.PostInfoVO;

import javax.annotation.Resource;

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
    private AdminService adminService;

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
        infoVO.setDeletable(adminService.editable(currentUser, userId));
        return infoVO;
    }
}