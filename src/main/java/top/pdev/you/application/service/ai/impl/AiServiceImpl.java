package top.pdev.you.application.service.ai.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.pdev.you.application.service.ai.AiService;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.ui.dto.AiChoiceMessageItemDTO;
import top.pdev.you.domain.ui.dto.AiMessageItemDTO;
import top.pdev.you.domain.ui.dto.AiRequestDTO;
import top.pdev.you.domain.ui.dto.AiResponseDTO;
import top.pdev.you.domain.ui.vm.AnswerResponse;
import top.pdev.you.infrastructure.config.bean.AiConfig;
import top.pdev.you.web.ai.command.AiAnswerCommand;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * AI 服务实现类
 * Created in 2023/12/16 18:35
 *
 * @author Peter1303
 */
@Slf4j
@Service
public class AiServiceImpl implements AiService {
    @Resource
    private AiConfig aiConfig;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public AnswerResponse ask(AiAnswerCommand command) {
        String question = command.getQuestion();
        AnswerResponse response = new AnswerResponse();
        // 构造 AI 请求问题
        AiRequestDTO requestDTO = new AiRequestDTO();
        requestDTO.setAppId(aiConfig.getAppId());
        // 构造问题 DTO
        List<AiMessageItemDTO> messages = new ArrayList<>(1);
        AiMessageItemDTO messageItemDTO = new AiMessageItemDTO();
        messageItemDTO.setContent(question);
        messages.add(messageItemDTO);
        requestDTO.setMessages(messages);
        // 请求
        HttpRequest request = HttpUtil.createPost(aiConfig.getApi() + "/chat/completions");
        try {
            String paramsJson = objectMapper.writeValueAsString(requestDTO);
            request.body(paramsJson);
            request.header("Token", aiConfig.getToken());
            try (HttpResponse httpResponse = request.execute()) {
                String json = httpResponse.body();
                AiResponseDTO aiResponseDTO = objectMapper.readValue(json, AiResponseDTO.class);
                List<AiChoiceMessageItemDTO> choices = aiResponseDTO.getChoices();
                if (!choices.isEmpty()) {
                    AiChoiceMessageItemDTO choiceMessageItemDTO = choices.get(0);
                    messageItemDTO = choiceMessageItemDTO.getMessage();
                    response.setAnswer(messageItemDTO.getContent().trim());
                }
            }
        } catch (JsonProcessingException e) {
            throw new InternalErrorException("系统错误", e);
        }
        return response;
    }
}
