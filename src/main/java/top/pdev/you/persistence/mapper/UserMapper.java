package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.User;

/**
 * 用户持久化
 * Created in 2022/10/2 13:16
 *
 * @author Peter1303
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
