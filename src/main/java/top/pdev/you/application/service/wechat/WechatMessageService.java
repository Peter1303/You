package top.pdev.you.application.service.wechat;

import top.pdev.you.common.entity.wechat.template.base.BaseTemplate;

/**
 * 微信消息服务
 * Created in 2022/5/3 23:27
 *
 * @author Peter1303
 */
public interface WechatMessageService {
    /**
     * 发送模板消息
     *
     * @param toUser       OPEN ID
     * @param pageUrl      跳转地址
     * @param baseTemplate 模板
     * @return 成功
     */
    boolean sendTemplateMessage(String toUser,
                                String pageUrl,
                                BaseTemplate baseTemplate);
}
