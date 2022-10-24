package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.data.AssociationManagerDO;

/**
 * 社团管理持久化
 * Created in 2022/10/24 16:59
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationManagerMapper extends BaseMapper<AssociationManagerDO> {
}
