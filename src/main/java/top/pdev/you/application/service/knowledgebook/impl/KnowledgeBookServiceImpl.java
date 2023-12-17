package top.pdev.you.application.service.knowledgebook.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import top.pdev.you.application.service.knowledgebook.KnowledgeBookService;
import top.pdev.you.domain.ui.dto.KnowledgeApiDTO;
import top.pdev.you.domain.ui.dto.KnowledgeApiItemDTO;
import top.pdev.you.domain.ui.vm.KnowledgeBookResponse;
import top.pdev.you.infrastructure.config.bean.AiConfig;
import top.pdev.you.web.knowledgebook.command.QuestionCommand;

import javax.annotation.Resource;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 知识库服务实现
 * Created in 2023/12/15 23:46
 *
 * @author Peter1303
 */
@Service
public class KnowledgeBookServiceImpl implements KnowledgeBookService {
    @Resource
    private AiConfig aiConfig;

    @Resource
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public KnowledgeBookResponse ask(QuestionCommand command) {
        String question = command.getQuestion();
        KnowledgeBookResponse response = new KnowledgeBookResponse();
        Map<String, Object> params = new HashMap<>();
        params.put("kbId", aiConfig.getKnowledgeId());
        params.put("text", question);
        HttpRequest request = HttpUtil.createPost(aiConfig.getDataSetApi() + "/searchTest");
        request.form(params);
        HttpCookie tokenCookie = new HttpCookie("token", aiConfig.getToken());
        request.cookie(tokenCookie);
        try (HttpResponse httpResponse = request.execute()) {
            String json = httpResponse.body();
            KnowledgeApiDTO apiDTO = objectMapper.readValue(json, KnowledgeApiDTO.class);
            List<KnowledgeApiItemDTO> data = apiDTO.getData();
            if (!data.isEmpty()) {
                KnowledgeApiItemDTO answer = data.get(0);
                response.setFound(true);
                response.setAnswer(answer.getAnswer());
                response.setScore(answer.getScore());
                response.setSource(answer.getSource());
            }
        }
        return response;
    }
}
