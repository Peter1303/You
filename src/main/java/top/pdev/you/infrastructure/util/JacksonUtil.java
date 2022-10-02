package top.pdev.you.infrastructure.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 序列化工具类
 * Created in 2022/5/10 10:15
 *
 * @author Peter1303
 */
public class JacksonUtil {
    private static ObjectMapper objectMapper;

    public static ObjectMapper getInstance() {
        if (objectMapper == null) {
            synchronized (ObjectMapper.class) {
                objectMapper = new ObjectMapper();
            }
        }
        return objectMapper;
    }
}
