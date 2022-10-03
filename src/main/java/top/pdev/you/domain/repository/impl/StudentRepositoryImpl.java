package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.mapper.StudentMapper;
import top.pdev.you.domain.repository.StudentRepository;

/**
 * 学生仓库持久类
 * Created in 2022/10/3 18:13
 *
 * @author Peter1303
 */
@Repository
public class StudentRepositoryImpl
        extends ServiceImpl<StudentMapper, StudentDO>
        implements StudentRepository {
}
