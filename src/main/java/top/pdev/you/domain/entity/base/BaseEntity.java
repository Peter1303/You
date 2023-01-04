package top.pdev.you.domain.entity.base;

import top.pdev.you.common.interfaces.VFunction;
import top.pdev.you.infrastructure.util.ValidateUtil;

/**
 * 基本实体
 * Created in 2022/9/30 22:32
 *
 * @author Peter1303
 */
public abstract class BaseEntity {
    /**
     * 非空
     *
     * @param param 参数函数接口
     */
    protected <V, R> void notNull(VFunction<V, R> param) {
        ValidateUtil.notNull(getClass(), this, param);
    }
}
