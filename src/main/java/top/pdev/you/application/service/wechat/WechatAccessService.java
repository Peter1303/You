package top.pdev.you.application.service.wechat;

/**
 * 微信接口访问凭证服务
 * Created in 2022/5/4 21:19
 *
 * @author Peter1303
 */
public interface WechatAccessService {
    /**
     * 获取微信接口调用凭证
     *
     * @return 凭证结果
     */
    String access();
}
