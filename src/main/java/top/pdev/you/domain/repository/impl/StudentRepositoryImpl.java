package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.mapper.StudentMapper;
import top.pdev.you.domain.repository.StudentRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 学生仓库持久类
 * Created in 2022/10/3 18:13
 *
 * @author Peter1303
 */
@Repository
public class StudentRepositoryImpl
        extends BaseRepository<StudentMapper, Student>
        implements StudentRepository {
    @Resource
    private StudentMapper mapper;

    @Override
    public Student findById(Long id) {
        Student student = getById(id);
        if (!Optional.ofNullable(student).isPresent()) {
            throw new BusinessException("找不到学生");
        }
        return student;
    }

    @Override
    public Student findByUserId(Long id) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUserId, id);
        Student student = mapper.selectOne(queryWrapper);
        if (!Optional.ofNullable(student).isPresent()) {
            throw new BusinessException("找不到学生");
        }
        return student;
    }

    @Override
    public boolean deleteByUserId(Long id) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getUserId, id);
        return remove(queryWrapper);
    }
}
