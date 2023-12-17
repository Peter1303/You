package top.pdev.you.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 知识库项 DTO
 * Created in 2023/12/17 13:14
 *
 * @author Peter1303
 */
@Data
public class KnowledgeItemDTO {
    @JsonProperty("a")
    private String answer;

    @JsonProperty("q")
    private String question;

    private String source;
}
