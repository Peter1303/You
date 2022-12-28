package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Teacher;
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
     * 查找
     *
     * @param id ID
     * @return {@link Teacher}
     */
    Teacher find(TeacherId id);

    /**
     * 获取 DO
     *
     * @param id ID
     * @return {@link TeacherDO}
     */
    TeacherDO getDO(TeacherId id);

    /**
     * 设置联系方式
     *
     * @param id      ID
     * @param contact 联系
     * @return boolean
     */
    boolean setContact(TeacherId id, String contact);

    /**
     * 删除
     *
     * @param id ID
     * @return boolean
     */
    boolean delete(TeacherId id);
}
