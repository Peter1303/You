package top.pdev.you.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.Teacher;

/**
 * 老师持久化
 * Created in 2022/10/3 16:30
 *
 * @author Peter1303
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
}
