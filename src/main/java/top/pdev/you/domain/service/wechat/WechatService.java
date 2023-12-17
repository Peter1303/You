package top.pdev.you.domain.service.wechat;

import top.pdev.you.domain.model.dto.WechatLoginDTO;

/**
 * 微信服务
 * Created in 2022/10/2 15:55
 *
 * @author Peter1303
 */
public interface WechatService {
    /**
     * 登录
     *
     * @param jsCode 临时登录凭证
     * @return {@link WechatLoginDTO}
     */
    WechatLoginDTO login(String jsCode);
}
