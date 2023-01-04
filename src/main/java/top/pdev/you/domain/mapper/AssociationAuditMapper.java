package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pdev.you.domain.entity.AssociationAudit;
import top.pdev.you.interfaces.model.dto.AssociationAuditDTO;

import java.util.List;

/**
 * 社团审核持久化
 * Created in 2022/11/9 12:20
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationAuditMapper extends BaseMapper<AssociationAudit> {
    /**
     * 获取审核列表
     *
     * @param associationId 社团 ID
     * @return {@link List}<{@link AssociationAuditDTO}>
     */
    List<AssociationAuditDTO> getAuditList(@Param("aid") Long associationId);
}
