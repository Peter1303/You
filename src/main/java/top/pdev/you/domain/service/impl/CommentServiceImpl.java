package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.factory.CommentFactory;
import top.pdev.you.domain.repository.CommentRepository;
import top.pdev.you.domain.repository.PostRepository;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.CommentService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.assembler.CommentAssembler;
import top.pdev.you.interfaces.model.vo.CommentInfoVO;
import top.pdev.you.interfaces.model.vo.req.AddCommentVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;

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
    public Result<?> postComments(IdVO idVO) {
        List<Comment> list = commentRepository.findByPostIdOrderByTimeDesc(idVO.getId());
        List<CommentInfoVO> result = list.stream().map(comment -> {
            CommentInfoVO infoVO = CommentAssembler.INSTANCE.convert(comment);
            Long userId = comment.getUserId();
            User user = userRepository.findById(userId);
            infoVO.setName(user.getRoleDomain().getName());
            return infoVO;
        }).collect(Collectors.toList());
        return Result.ok(result);
    }

    @Override
    public Result<?> add(AddCommentVO addCommentVO) {
        Long id = addCommentVO.getId();
        Post post = postRepository.findById(id);
        Comment comment = commentFactory.newComment();
        comment.setComment(addCommentVO.getComment());
        comment.save(post);
        return Result.ok();
    }

    @Override
    public Result<?> delete(IdVO idVO) {
        Comment comment = commentRepository.findById(idVO.getId());
        comment.delete();
        return Result.ok();
    }
}
