package top.pdev.you.infrastructure.util;

import top.pdev.you.common.interfaces.VFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Lambda 工具
 * Created in 2022/6/19 12:12
 *
 * @author Peter1303
 */
public class LambdaUtil {
    /**
     * 得到实现方法名称
     *
     * @param function 函数
     * @return {@link String}
     */
    public static <V, R> String getImplMethodName(VFunction<V, R> function) {
        try {
            Method writeReplace = function.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object sl = writeReplace.invoke(function);
            SerializedLambda serializedLambda = (SerializedLambda) sl;
            return serializedLambda.getImplMethodName();
        } catch (NoSuchMethodException
                 | InvocationTargetException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取字段名
     *
     * @param function 函数
     * @return {@link String}
     */
    public static <V, R> String getFieldName(VFunction<V, R> function) {
        String name = getImplMethodName(function);
        if (!name.startsWith("get")) {
            return null;
        }
        name = name.substring(3);
        return StringUtil.toLowercaseCamel(name);
    }
}
