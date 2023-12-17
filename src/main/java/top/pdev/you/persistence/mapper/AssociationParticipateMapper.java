package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.AssociationParticipant;

/**
 * 社团参与持久化
 * Created in 2022/10/26 10:40
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationParticipateMapper extends BaseMapper<AssociationParticipant> {
}
