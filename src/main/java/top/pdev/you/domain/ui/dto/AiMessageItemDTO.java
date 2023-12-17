package top.pdev.you.domain.ui.dto;

import lombok.Data;

/**
 * AI 消息项 DTO
 * Created in 2023/12/16 18:48
 *
 * @author Peter1303
 */
@Data
public class AiMessageItemDTO {
    private String role = "user";
    private String content;
}
