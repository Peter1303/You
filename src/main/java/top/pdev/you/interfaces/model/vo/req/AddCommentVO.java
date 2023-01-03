package top.pdev.you.interfaces.model.vo.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 添加评论 VO
 * Created in 2023/1/2 21:00
 *
 * @author Peter1303
 */
@Data
public class AddCommentVO extends IdVO {
    @Length(min = 1, max = 255)
    @NotNull
    private String comment;
}
