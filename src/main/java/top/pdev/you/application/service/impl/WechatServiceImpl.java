package top.pdev.you.application.service.impl;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import top.pdev.you.application.service.WechatService;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.infrastructure.config.WechatProperties;
import top.pdev.you.infrastructure.result.ResultCode;
import top.pdev.you.infrastructure.util.JacksonUtil;
import top.pdev.you.interfaces.model.dto.WechatLoginDTO;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 微信服务实现类
 * Created in 2022/10/2 15:56
 *
 * @author Peter1303
 */
@Service
public class WechatServiceImpl implements WechatService {
    @Resource
    private WechatProperties wechatProperties;

    @Override
    public WechatLoginDTO login(String jsCode) {
        String appId = wechatProperties.getAppId();
        String secret = wechatProperties.getSecretKey();
        StrBuilder builder = new StrBuilder();
        builder.append("https://api.weixin.qq.com/sns/jscode2session?appid=")
                .append(appId)
                .append("&secret=")
                .append(secret)
                .append("&js_code=")
                .append(jsCode)
                .append("&grant_type=authorization_code");
        String json = HttpUtil.get(builder.toString());
        try {
            WechatLoginDTO loginDTO = JacksonUtil.getInstance().readValue(json, WechatLoginDTO.class);
            if (Optional.ofNullable(loginDTO.getErrCode()).isPresent()) {
                throw new BusinessException(ResultCode.FAILED, "微信登录失败");
            }
            return loginDTO;
        } catch (JsonProcessingException e) {
            throw new InternalErrorException("反序列化微信出现错误", e);
        }
    }
}
