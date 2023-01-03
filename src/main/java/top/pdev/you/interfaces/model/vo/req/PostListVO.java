package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;

import javax.validation.constraints.Positive;

/**
 * 帖子列表 VO
 * Created in 2023/1/2 17:05
 *
 * @author Peter1303
 */
@Data
public class PostListVO {
    @Positive
    private Long id;
}