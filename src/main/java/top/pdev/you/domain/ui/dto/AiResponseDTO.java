package top.pdev.you.domain.ui.dto;

import lombok.Data;

import java.util.List;

/**
 * AI 回答 DTO
 * Created in 2023/12/16 18:55
 *
 * @author Peter1303
 */
@Data
public class AiResponseDTO {
    private List<AiChoiceMessageItemDTO> choices;
}
