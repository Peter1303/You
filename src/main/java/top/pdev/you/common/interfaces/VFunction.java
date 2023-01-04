package top.pdev.you.common.interfaces;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 字段接口
 * Created in 2023/1/4 14:53
 *
 * @author Peter1303
 */
public interface VFunction<V, R> extends Function<V, R>, Serializable {
}
