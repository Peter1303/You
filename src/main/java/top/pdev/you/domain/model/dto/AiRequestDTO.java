package top.pdev.you.domain.model.dto;

import lombok.Data;

import java.util.List;

/**
 * AI 请求 DTO
 * Created in 2023/12/16 18:47
 *
 * @author Peter1303
 */
@Data
public class AiRequestDTO {
    private String appId;
    private Boolean stream = false;
    private Boolean detail = false;
    private List<AiMessageItemDTO> messages;
}
