package top.pdev.you.domain.service.comment.impl;

import cn.hutool.core.date.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.comment.CommentService;
import top.pdev.you.domain.service.user.UserService;
import top.pdev.you.domain.ui.dto.CommentInfoDTO;
import top.pdev.you.infrastructure.factory.CommentFactory;
import top.pdev.you.infrastructure.mapper.CommentMapper;
import top.pdev.you.persistence.repository.CommentRepository;
import top.pdev.you.persistence.repository.PostRepository;
import top.pdev.you.persistence.repository.UserRepository;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.comment.command.AddCommentCommand;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论服务实现类
 * Created in 2023/1/2 20:58
 *
 * @author Peter1303
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private UserService userService;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private PostRepository postRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private CommentFactory commentFactory;

    @Override
    public List<CommentInfoDTO> postComments(IdCommand idCommand) {
        List<Comment> list = commentRepository.findByPostIdOrderByTimeDesc(idCommand.getId());
        return list.stream()
                .map(comment -> {
                    CommentInfoDTO infoVO = CommentMapper.INSTANCE.convert(comment);
                    Long userId = comment.getUserId();
                    User user = userRepository.findById(userId);
                    infoVO.setName(userService.getRoleDomain(user).getName());
                    return infoVO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void add(User user, AddCommentCommand addCommentVO) {
        Long id = addCommentVO.getId();
        Post post = postRepository.findById(id);
        Comment comment = commentFactory.newComment();
        comment.setUserId(user.getId());
        comment.setComment(addCommentVO.getComment());
        comment.setPostId(post.getId());
        comment.setTime(DateTime.now().toLocalDateTime());
        if (!commentRepository.save(comment)) {
            throw new BusinessException("无法评论");
        }
    }

    @Transactional
    @Override
    public void delete(IdCommand idCommand) {
        if (!commentRepository.deleteById(idCommand.getId())) {
            throw new BusinessException("删除评论失败");
        }
    }
}
