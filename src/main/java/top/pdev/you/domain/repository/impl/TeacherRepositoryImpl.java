package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.TeacherDO;
import top.pdev.you.domain.mapper.TeacherMapper;
import top.pdev.you.domain.repository.TeacherRepository;

/**
 * 老师仓库实现类
 * Created in 2022/10/3 16:31
 *
 * @author Peter1303
 */
@Repository
public class TeacherRepositoryImpl
        extends ServiceImpl<TeacherMapper, TeacherDO>
        implements TeacherRepository {
}
