package top.pdev.you.domain.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 知识库接口 DTO
 * Created in 2023/12/16 0:09
 *
 * @author Peter1303
 */
@Data
public class KnowledgeApiDTO {
    private Integer code;
    private List<KnowledgeApiItemDTO> data;
}
