package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.data.StudentDO;
import top.pdev.you.domain.entity.types.StudentId;

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
    Student find(StudentId id);

    /**
     * 获取 DO
     *
     * @param id ID
     * @return {@link StudentDO}
     */
    StudentDO getDO(StudentId id);

    /**
     * 设置联系方式
     *
     * @param id      ID
     * @param contact 联系
     * @return boolean
     */
    boolean setContact(StudentId id, String contact);

    /**
     * 删除
     *
     * @param id ID
     * @return boolean
     */
    boolean delete(StudentId id);
}
