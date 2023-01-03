package top.pdev.you.domain.repository.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 基类仓库
 * Created in 2022/10/29 13:48
 *
 * @author Peter1303
 */
public class BaseRepository<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T> {
}
