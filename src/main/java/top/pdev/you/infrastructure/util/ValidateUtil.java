package top.pdev.you.infrastructure.util;

import com.esotericsoftware.reflectasm.MethodAccess;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.common.interfaces.VFunction;

import java.util.Optional;

/**
 * 检验工具
 * Created in 2023/1/4 15:06
 *
 * @author Peter1303
 */
public class ValidateUtil {
    private ValidateUtil() {
    }

    /**
     * 非空
     *
     * @param clazz  类
     * @param entity 实体
     * @param param  参数函数接口
     */
    public static <V, R> void notNull(Class<?> clazz, Object entity, VFunction<V, R> param) {
        String methodName = LambdaUtil.getImplMethodName(param);
        String fieldName = LambdaUtil.getFieldName(param);
        MethodAccess methodAccess = MethodAccess.get(clazz);
        int index = methodAccess.getIndex(methodName);
        Object value = methodAccess.invoke(entity, index);
        // 检查是否为空
        Optional.ofNullable(value)
                .orElseThrow(() -> new InternalErrorException(fieldName + " 为空"));
    }
}
