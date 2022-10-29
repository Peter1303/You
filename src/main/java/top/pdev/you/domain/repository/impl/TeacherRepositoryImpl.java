package top.pdev.you.domain.repository.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.stereotype.Repository;
import top.pdev.you.domain.entity.data.TeacherDO;
import top.pdev.you.domain.entity.types.TeacherId;
import top.pdev.you.domain.mapper.TeacherMapper;
import top.pdev.you.domain.repository.TeacherRepository;
import top.pdev.you.domain.repository.base.BaseRepository;

import javax.annotation.Resource;

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
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public TeacherDO getDO(TeacherId id) {
        return teacherMapper.selectById(id.getId());
    }

    @Override
    public boolean setContact(TeacherId id, String contact) {
        checkId(id);
        LambdaUpdateWrapper<TeacherDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeacherDO::getId, id.getId())
                .set(TeacherDO::getContact, contact);
        return teacherMapper.update(null, updateWrapper) != 0;
    }

    @Override
    public boolean delete(TeacherId id) {
        checkId(id);
        return teacherMapper.deleteById(id.getId()) != 0;
    }
}
