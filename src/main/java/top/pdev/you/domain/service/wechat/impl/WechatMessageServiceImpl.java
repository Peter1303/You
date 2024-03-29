package top.pdev.you.domain.service.wechat.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.pdev.you.common.entity.wechat.WechatTemplateData;
import top.pdev.you.common.entity.wechat.result.base.WechatBaseResult;
import top.pdev.you.common.entity.wechat.template.base.BaseTemplate;
import top.pdev.you.domain.service.wechat.WechatAccessService;
import top.pdev.you.domain.service.wechat.WechatMessageService;

import javax.annotation.Resource;

/**
 * 微信应用消息服务实现
 * Created in 2022/5/3 23:27
 *
 * @author Peter1303
 */
@Slf4j
@Service
public class WechatMessageServiceImpl implements WechatMessageService {
    @Resource
    private WechatAccessService wechatAccessService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean sendTemplateMessage(String toUser, String pageUrl, BaseTemplate baseTemplate) {
        String accessToken = wechatAccessService.access();
        if (StrUtil.isEmpty(accessToken)) {
            // 还是拿不到凭证
            return false;
        }
        // 发送
        final String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="
                + accessToken;
        WechatTemplateData templateData = new WechatTemplateData();
        templateData.setToUser(toUser);
        templateData.setTemplateId(baseTemplate.getId());
        templateData.setTemplateData(baseTemplate.getTemplateData());
        if (StrUtil.isNotBlank(pageUrl)) {
            templateData.setPageUrl(pageUrl);
        }
        String data;
        try {
            data = objectMapper.writeValueAsString(templateData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
        String json = HttpUtil.post(url, data);
        try {
            WechatBaseResult wechatBaseResult = objectMapper.readValue(json, WechatBaseResult.class);
            if (wechatBaseResult != null) {
                if (wechatBaseResult.getErrcode() == 0) {
                    return true;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
