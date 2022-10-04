package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.interfaces.model.dto.ClassInfoDTO;

import java.util.List;

/**
 * 班级持久化
 * Created in 2022/10/3 18:19
 *
 * @author Peter1303
 */
@Mapper
public interface ClassMapper extends BaseMapper<ClassDO> {
    /**
     * 获取班级信息列表
     *
     * @param name 名字
     * @return {@link List}<{@link ClassInfoDTO}>
     */
    List<ClassInfoDTO> getClassInfoList(@Param("name") String name);
}
