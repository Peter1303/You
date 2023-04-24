package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.Institute;

/**
 * 学院持久化
 * Created in 2022/10/26 11:37
 *
 * @author Peter1303
 */
@Mapper
public interface InstituteMapper extends BaseMapper<Institute> {
}
