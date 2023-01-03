package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.data.TeacherDO;

/**
 * 老师仓库
 * Created in 2022/10/3 16:30
 *
 * @author Peter1303
 */
public interface TeacherRepository extends IService<TeacherDO> {
    /**
     * 通过 ID 查找
     *
     * @param id ID
     * @return {@link Teacher}
     */
    Teacher findById(Long id);

    /**
     * 通过用户 ID 查找
     *
     * @param id ID
     * @return {@link TeacherDO}
     */
    Teacher findByUserId(Long id);
}
