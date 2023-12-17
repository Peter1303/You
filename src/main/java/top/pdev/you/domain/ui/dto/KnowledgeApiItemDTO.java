package top.pdev.you.domain.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 知识库数据 DTO
 * Created in 2023/12/16 0:10
 *
 * @author Peter1303
 */
@Data
public class KnowledgeApiItemDTO {
    @JsonProperty("a")
    private String answer;
    private String source;
    private Float score;
}
