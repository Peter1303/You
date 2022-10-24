package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.data.AssociationDO;

/**
 * 社团持久化
 * Created in 2022/10/19 13:14
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationMapper extends BaseMapper<AssociationDO> {
}
