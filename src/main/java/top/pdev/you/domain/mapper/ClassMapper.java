package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.interfaces.model.dto.ClassInfoDTO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

import java.util.List;

/**
 * 班级持久化
 * Created in 2022/10/3 18:19
 *
 * @author Peter1303
 */
@Mapper
public interface ClassMapper extends BaseMapper<Clazz> {
    /**
     * 获取班级信息列表
     *
     * @param searchVO 搜索 VO
     * @return {@link List}<{@link ClassInfoDTO}>
     */
    List<ClassInfoDTO> getClassInfoList(@Param("vo") SearchVO searchVO);
}
