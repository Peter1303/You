package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
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
     * 获取 DO
     *
     * @param id ID
     * @return {@link StudentDO}
     */
    StudentDO getDO(StudentId id);
}
