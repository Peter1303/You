package top.pdev.you.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Student;

/**
 * 学生仓库
 * Created in 2022/10/3 18:12
 *
 * @author Peter1303
 */
public interface StudentRepository extends IService<Student> {
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

    /**
     * 通过用户 ID删除
     *
     * @param id ID
     * @return boolean
     */
    boolean deleteByUserId(Long id);
}
