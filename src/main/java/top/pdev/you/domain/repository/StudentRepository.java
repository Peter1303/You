package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.data.StudentDO;

/**
 * 学生仓库
 * Created in 2022/10/3 18:12
 *
 * @author Peter1303
 */
public interface StudentRepository extends IService<StudentDO> {
    /**
     * 查找
     *
     * @param id ID
     * @return {@link Student}
     */
    Student findById(Long id);

    /**
     * 通过用户 ID 查找
     *
     * @param id ID
     * @return {@link Student}
     */
    Student findByUserId(Long id);
}
