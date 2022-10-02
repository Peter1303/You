package top.pdev.you.interfaces.model.vo;

import lombok.Data;
import top.pdev.you.interfaces.model.vo.base.BaseVo;

/**
 * 登录结果
 * Created in 2022/10/2 18:15
 *
 * @author Peter1303
 */
@Data
public class LoginResultVO extends BaseVo {
    private String token;
}
