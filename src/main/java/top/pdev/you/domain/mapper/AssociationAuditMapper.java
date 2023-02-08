package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.AssociationAudit;

/**
 * 社团审核持久化
 * Created in 2022/11/9 12:20
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationAuditMapper extends BaseMapper<AssociationAudit> {
}
