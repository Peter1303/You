package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.interfaces.model.dto.AssociationBaseInfoDTO;
import top.pdev.you.interfaces.model.dto.AssociationInfoDTO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

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
     * 获取信息列表
     *
     * @param searchVO 搜索 VO
     * @return {@link List}<{@link AssociationInfoDTO}>
     */
    List<AssociationInfoDTO> getInfoList(@Param("vo") SearchVO searchVO);

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
