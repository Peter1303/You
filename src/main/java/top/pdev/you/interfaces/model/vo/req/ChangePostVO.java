package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 修改帖子 VO
 * Created in 2023/1/2 18:53
 *
 * @author Peter1303
 */
@Data
public class ChangePostVO extends IdVO {
    @Positive
    private Long topicId;

    @Length(min = 1, max = 4096)
    @NotNull
    private String content;
}
