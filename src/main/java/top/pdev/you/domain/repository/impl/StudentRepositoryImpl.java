package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.entity.types.StudentId;
import top.pdev.you.domain.mapper.StudentMapper;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;

/**
 * 学生仓库持久类
 * Created in 2022/10/3 18:13
 *
 * @author Peter1303
 */
@Repository
public class StudentRepositoryImpl
        extends BaseRepository<StudentMapper, StudentDO>
        implements StudentRepository {
    @Resource
    private StudentMapper studentMapper;

    @Override
    public StudentDO getDO(StudentId id) {
        return studentMapper.selectById(id.getId());
    }

    @Override
    public boolean setContact(StudentId id, String contact) {
        Optional.ofNullable(id).orElseThrow(() -> new InternalErrorException("ID 为空"));
        LambdaUpdateWrapper<StudentDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StudentDO::getId, id.getId())
                .set(StudentDO::getContact, contact);
        return studentMapper.update(null, updateWrapper) != 0;
    }
}
