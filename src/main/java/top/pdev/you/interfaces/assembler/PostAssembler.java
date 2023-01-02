package top.pdev.you.interfaces.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import top.pdev.you.domain.entity.Post;
import top.pdev.you.interfaces.model.vo.PostInfoVO;

/**
 * 帖子封装器
 * Created in 2023/1/2 17:41
 *
 * @author Peter1303
 */
@Mapper
public interface PostAssembler {
    PostAssembler INSTANCE = Mappers.getMapper(PostAssembler.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "time", source = "time")
    PostInfoVO convert(Post post);
}
