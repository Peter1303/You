package top.pdev.you.common.exception;

import lombok.Getter;

/**
 * 配置异常
 * Created in 2022/5/18 18:15
 *
 * @author Peter1303
 */
public class ConfigurationException extends Exception {
    @Getter
    private String message = "配置有误";

    public ConfigurationException() {
    }

    public ConfigurationException(String message) {
        this.message = message;
    }
}
