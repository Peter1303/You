package top.pdev.you.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 添加知识库 DTO
 * Created in 2023/12/17 13:16
 *
 * @author Peter1303
 */
@Data
public class AddKnowledgeDTO {
    private String mode = "index";
    private List<KnowledgeItemDTO> data;

    @JsonProperty("kbId")
    private String knowledgeBookId;
}
