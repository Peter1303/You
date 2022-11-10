package top.pdev.you.common.entity.wechat.result.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 微信返回结果基类
 * Created in 2022/5/2 12:01
 *
 * @author Peter1303
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WechatBaseResult {
    private Integer errcode;
}
