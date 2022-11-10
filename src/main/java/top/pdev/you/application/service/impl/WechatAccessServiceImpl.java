package top.pdev.you.application.service.impl;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.pdev.you.application.service.WechatAccessService;
import top.pdev.you.common.constant.RedisKey;
import top.pdev.you.common.entity.wechat.result.WechatAccessResult;
import top.pdev.you.infrastructure.config.WechatProperties;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.util.JacksonUtil;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 微信接口访问凭证服务实现
 * Created in 2022/5/4 21:20
 *
 * @author Peter1303
 */
@Slf4j
@Service
public class WechatAccessServiceImpl implements WechatAccessService {
    @Resource
    private WechatProperties wechatProperties;

    @Resource
    private RedisService redisService;

    @Override
    public String access() {
        if (redisService.hasKey(RedisKey.getWechatAccessToken())) {
            return redisService.getObject(RedisKey.getWechatAccessToken(), String.class);
        }
        String appId = wechatProperties.getAppId();
        String secret = wechatProperties.getSecretKey();
        StrBuilder builder = new StrBuilder();
        builder.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=")
                .append(appId)
                .append("&secret=")
                .append(secret);
        String json = HttpUtil.get(builder.toString());
        String accessToken = null;
        try {
            WechatAccessResult accessResult = JacksonUtil.getInstance()
                    .readValue(json, WechatAccessResult.class);
            // 没有错误
            if (accessResult != null) {
                if (accessResult.getErrcode() == null) {
                    accessToken = accessResult.getAccessToken();
                    Long expiresIn = accessResult.getExpiresIn();
                    if (Optional.ofNullable(expiresIn).isPresent()) {
                        redisService.set(RedisKey.getWechatAccessToken(),
                                accessToken,
                                expiresIn - 100,
                                TimeUnit.SECONDS);
                    }
                }
            }
            return accessToken;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
