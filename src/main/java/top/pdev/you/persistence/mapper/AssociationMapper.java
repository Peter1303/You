package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.ui.dto.AssociationBaseInfoDTO;

import java.util.List;

/**
 * 社团持久化
 * Created in 2022/10/19 13:14
 *
 * @author Peter1303
 */
@Mapper
public interface AssociationMapper extends BaseMapper<Association> {
    /**
     * 通过成员获取列表
     *
     * @param studentId 学生 ID
     * @return {@link List}<{@link AssociationBaseInfoDTO}>
     */
    List<AssociationBaseInfoDTO> getListByParticipant(@Param("sid") long studentId);

    /**
     * 通过管理者获取列表
     *
     * @param uid uid
     * @return {@link List}<{@link AssociationBaseInfoDTO}>
     */
    List<AssociationBaseInfoDTO> getListByAdmin(@Param("uid") long uid);
}
