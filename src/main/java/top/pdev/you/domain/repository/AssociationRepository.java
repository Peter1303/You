package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.data.AssociationDO;
import top.pdev.you.domain.entity.types.AssociationId;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.interfaces.model.dto.AssociationInfoDTO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

import java.util.List;

/**
 * 社团管理仓库
 * Created in 2022/10/23 21:38
 *
 * @author Peter1303
 */
public interface AssociationRepository extends IService<AssociationDO> {
    /**
     * 查找
     *
     * @param associationId 协会 ID
     * @return {@link Association}
     */
    Association find(AssociationId associationId);

    /**
     * 获取
     *
     * @param student 学生
     * @return {@link Association}
     */
    Association getOne(Student student);

    /**
     * 获取
     *
     * @param id ID
     * @return {@link Association}
     */
    Association getOne(Id id);

    /**
     * 获取管理列表
     *
     * @param teacher 老师
     * @return {@link List}<{@link AssociationDO}>
     */
    List<AssociationDO> getManagedList(Teacher teacher);

    /**
     * 学生参加名单
     *
     * @param student 学生
     * @return {@link List}<{@link AssociationDO}>
     */
    List<AssociationDO> ofStudentList(Student student);

    /**
     * 获取信息列表
     *
     * @param searchVO 搜索 VO
     * @return {@link List}<{@link AssociationInfoDTO}>
     */
    List<AssociationInfoDTO> getInfoList(SearchVO searchVO);

    /**
     * 存在
     *
     * @param name 名字
     * @return boolean
     */
    boolean exists(String name);
}
