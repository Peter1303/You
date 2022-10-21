package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.data.TeacherDO;
import top.pdev.you.domain.entity.types.TeacherId;

/**
 * 老师仓库
 * Created in 2022/10/3 16:30
 *
 * @author Peter1303
 */
public interface TeacherRepository extends IService<TeacherDO> {
    /**
     * 获取 DO
     *
     * @param id ID
     * @return {@link TeacherDO}
     */
    TeacherDO getDO(TeacherId id);
}
