package top.pdev.you.domain.repository.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.entity.types.Id;

import java.util.Optional;

/**
 * 基类仓库
 * Created in 2022/10/29 13:48
 *
 * @author Peter1303
 */
public class BaseRepository<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T> {
    /**
     * 检查 ID
     *
     * @param id ID
     */
    protected void checkId(Id id) {
        Optional.ofNullable(id).orElseThrow(() -> new InternalErrorException("ID 为空"));
    }

    protected void check(User user) {
        Optional.ofNullable(user).orElseThrow(() -> new BusinessException("找不到用户"));
    }
}
