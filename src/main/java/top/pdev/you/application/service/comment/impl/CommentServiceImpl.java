package top.pdev.you.application.service.comment.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.comment.CommentService;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.CommentFactory;
import top.pdev.you.domain.repository.CommentRepository;
import top.pdev.you.domain.repository.PostRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.infrastructure.assembler.CommentAssembler;
import top.pdev.you.web.comment.command.CommentInfoCommand;
import top.pdev.you.web.comment.command.AddCommentCommand;
import top.pdev.you.web.command.IdCommand;

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
    private CommentRepository commentRepository;

    @Resource
    private PostRepository postRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private CommentFactory commentFactory;

    @Override
    public Result<?> postComments(IdCommand idCommand) {
        List<Comment> list = commentRepository.findByPostIdOrderByTimeDesc(idCommand.getId());
        List<CommentInfoCommand> result = list.stream().map(comment -> {
            CommentInfoCommand infoVO = CommentAssembler.INSTANCE.convert(comment);
            Long userId = comment.getUserId();
            User user = userRepository.findById(userId);
            infoVO.setName(user.getRoleDomain().getName());
            return infoVO;
        }).collect(Collectors.toList());
        return Result.ok(result);
    }

    @Transactional
    @Override
    public Result<?> add(User user, AddCommentCommand addCommentVO) {
        Long id = addCommentVO.getId();
        Post post = postRepository.findById(id);
        Comment comment = commentFactory.newComment();
        comment.setUserId(user.getId());
        comment.setComment(addCommentVO.getComment());
        comment.save(post);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> delete(IdCommand idCommand) {
        Comment comment = commentRepository.findById(idCommand.getId());
        comment.delete();
        return Result.ok();
    }
}
