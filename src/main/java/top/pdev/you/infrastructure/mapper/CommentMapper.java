package top.pdev.you.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.Comment;
import top.pdev.you.domain.model.dto.CommentInfoDTO;

/**
 * 评论封装器
 * Created in 2023/1/2 22:28
 *
 * @author Peter1303
 */
@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "time", source = "time")
    CommentInfoDTO convert(Comment comment);
}
