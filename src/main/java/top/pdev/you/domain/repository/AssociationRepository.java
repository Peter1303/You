package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.interfaces.model.dto.AssociationInfoDTO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

import java.util.Arrays;
import java.util.List;

/**
 * 社团管理仓库
 * Created in 2022/10/23 21:38
 *
 * @author Peter1303
 */
public interface AssociationRepository extends IService<Association> {
    /**
     * 查找
     *
     * @param associationId 协会 ID
     * @return {@link Association}
     */
    Association findById(Long associationId);

    /**
     * 获取管理列表
     *
     * @param teacher 老师
     * @return {@link List}<{@link Association}>
     */
    List<Association> getManagedList(Teacher teacher);

    /**
     * 学生参加名单
     *
     * @param student 学生
     * @return {@link List}<{@link Association}>
     */
    List<Association> ofStudentList(Student student);

    /**
     * 查找名字包含
     *
     * @param name 名字
     * @return {@link List}<{@link Association}>
     */
    List<Association> findNameContaining(String name);

    /**
     * 存在
     *
     * @param name 名字
     * @return boolean
     */
    boolean existsByName(String name);

    /**
     * 通过 ID 删除
     *
     * @param id ID
     * @return boolean
     */
    boolean deleteById(Long id);
}
