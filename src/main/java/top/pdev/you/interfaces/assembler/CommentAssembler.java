package top.pdev.you.interfaces.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.interfaces.model.vo.CommentInfoVO;

/**
 * 评论封装器
 * Created in 2023/1/2 22:28
 *
 * @author Peter1303
 */
@Mapper
public interface CommentAssembler {
    CommentAssembler INSTANCE = Mappers.getMapper(CommentAssembler.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "time", source = "time")
    CommentInfoVO convert(Comment comment);
}