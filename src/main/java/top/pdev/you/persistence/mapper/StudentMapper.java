package top.pdev.you.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.pdev.you.domain.entity.Student;

/**
 * 学生持久化
 * Created in 2022/10/3 18:12
 *
 * @author Peter1303
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
