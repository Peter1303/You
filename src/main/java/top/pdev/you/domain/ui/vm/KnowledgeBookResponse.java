package top.pdev.you.domain.ui.vm;

import lombok.Data;

/**
 * 知识库响应
 * Created in 2023/12/16 0:03
 *
 * @author Peter1303
 */
@Data
public class KnowledgeBookResponse {
    private Boolean found = false;
    private String answer;
    private String source;
    private Float score = 0.0f;
}
