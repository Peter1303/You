package top.pdev.you.domain.entity.types;

import lombok.Data;

/**
 * 微信 ID
 * Created in 2022/10/1 16:08
 *
 * @author Peter1303
 */
@Data
public class WechatId {
    private String id;

    public WechatId(String id) {
        this.id = id;
    }
}
