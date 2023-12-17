package top.pdev.you.domain.service.ai.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.command.ai.AddKnowledgeCommand;
import top.pdev.you.domain.command.ai.AiAnswerCommand;
import top.pdev.you.domain.model.dto.AddKnowledgeDTO;
import top.pdev.you.domain.model.dto.AddKnowledgeResponseDTO;
import top.pdev.you.domain.model.dto.AiChoiceMessageItemDTO;
import top.pdev.you.domain.model.dto.AiMessageItemDTO;
import top.pdev.you.domain.model.dto.AiRequestDTO;
import top.pdev.you.domain.model.dto.AiResponseDTO;
import top.pdev.you.domain.model.dto.KnowledgeItemDTO;
import top.pdev.you.domain.model.vm.AnswerResponse;
import top.pdev.you.domain.service.ai.AiService;
import top.pdev.you.infrastructure.config.bean.AiConfig;
import top.pdev.you.infrastructure.result.ResultCode;

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
        String json = requestApi(aiConfig.getApi() + "/chat/completions", requestDTO);
        try {
            AiResponseDTO aiResponseDTO = objectMapper.readValue(json, AiResponseDTO.class);
            List<AiChoiceMessageItemDTO> choices = aiResponseDTO.getChoices();
            if (!choices.isEmpty()) {
                AiChoiceMessageItemDTO choiceMessageItemDTO = choices.get(0);
                messageItemDTO = choiceMessageItemDTO.getMessage();
                response.setAnswer(messageItemDTO.getContent().trim());
            }
        } catch (JsonProcessingException e) {
            throw new InternalErrorException("系统错误", e);
        }
        return response;
    }

    @Override
    public void add(@Validated AddKnowledgeCommand command) {
        String knowledgeBookId = command.getKnowledgeBookId();
        String point = command.getPoint();
        String content = command.getContent();
        String source = command.getSource();

        AddKnowledgeDTO addKnowledgeDTO = new AddKnowledgeDTO();
        addKnowledgeDTO.setKnowledgeBookId(aiConfig.getKnowledgeId());
        // 添加数据
        KnowledgeItemDTO knowledgeItemDTO = new KnowledgeItemDTO();
        knowledgeItemDTO.setQuestion(point);
        knowledgeItemDTO.setAnswer(content);
        knowledgeItemDTO.setSource(source);
        List<KnowledgeItemDTO> data = new ArrayList<>(1);
        data.add(knowledgeItemDTO);
        addKnowledgeDTO.setData(data);
        // 请求
        String json = requestApi(aiConfig.getDataSetApi() + "/data/pushData", addKnowledgeDTO);
        try {
            AddKnowledgeResponseDTO responseDTO = objectMapper.readValue(json, AddKnowledgeResponseDTO.class);
            Integer code = responseDTO.getCode();
            if (code == null || code != ResultCode.OK.getCode()) {
                throw new InternalErrorException("无法调用 AI");
            }
        } catch (JsonProcessingException e) {
            throw new InternalErrorException("系统错误", e);
        }
    }

    /**
     * 请求接口
     *
     * @param api    API
     * @param params params 参数
     * @return {@link String}
     */
    private String requestApi(String api, Object params) {
        // 请求
        HttpRequest request = HttpUtil.createPost(api);
        try {
            String paramsJson = objectMapper.writeValueAsString(params);
            request.body(paramsJson);
            request.header("Token", aiConfig.getToken());
            try (HttpResponse httpResponse = request.execute()) {
                return httpResponse.body();
            }
        } catch (JsonProcessingException e) {
            throw new InternalErrorException("系统错误", e);
        }
    }
}
