package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.data.TeacherDO;
import top.pdev.you.domain.mapper.TeacherMapper;
import top.pdev.you.domain.repository.TeacherRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import java.util.Optional;

/**
 * 老师仓库实现类
 * Created in 2022/10/3 16:31
 *
 * @author Peter1303
 */
@Repository
public class TeacherRepositoryImpl
        extends BaseRepository<TeacherMapper, TeacherDO>
        implements TeacherRepository {
    @Override
    public Teacher findById(Long id) {
        TeacherDO teacherDO = getById(id);
        if (!Optional.ofNullable(teacherDO).isPresent()) {
            throw new BusinessException("找不到老师");
        }
        return new Teacher(teacherDO);
    }

    @Override
    public Teacher findByUserId(Long id) {
        LambdaQueryWrapper<TeacherDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeacherDO::getUserId, id);
        TeacherDO teacherDO = getOne(queryWrapper);
        if (!Optional.ofNullable(teacherDO).isPresent()) {
            throw new BusinessException("找不到老师");
        }
        return new Teacher(teacherDO);
    }
}
