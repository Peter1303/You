package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.data.ClassDO;

/**
 * 班级持久化
 * Created in 2022/10/3 18:19
 *
 * @author Peter1303
 */
@Mapper
public interface ClassMapper extends BaseMapper<ClassDO> {
}
