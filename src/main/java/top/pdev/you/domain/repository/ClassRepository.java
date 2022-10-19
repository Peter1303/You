package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.domain.entity.data.ClassDO;
import top.pdev.you.interfaces.model.dto.ClassInfoDTO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

import java.util.List;

/**
 * 班级仓库
 * Created in 2022/10/3 18:20
 *
 * @author Peter1303
 */
public interface ClassRepository extends IService<ClassDO> {
    /**
     * 查找
     *
     * @param id ID
     * @return {@link Clazz}
     */
    Clazz find(Long id);

    /**
     * 获取班级信息
     *
     * @param condition 条件
     * @return {@link List}<{@link ClassInfoDTO}>
     */
    List<ClassInfoDTO> getClassInfo(SearchVO condition);
}
