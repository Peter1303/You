package top.pdev.you.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Clazz;
import top.pdev.you.domain.ui.dto.ClassInfoDTO;
import top.pdev.you.web.query.command.SearchCommand;

import java.util.List;

/**
 * 班级仓库
 * Created in 2022/10/3 18:20
 *
 * @author Peter1303
 */
public interface ClassRepository extends IService<Clazz> {
    /**
     * 查找
     *
     * @param id ID
     * @return {@link Clazz}
     */
    Clazz findById(Long id);

    /**
     * 获取班级信息
     *
     * @param searchCommand 搜索 VO
     * @return {@link List}<{@link ClassInfoDTO}>
     */
    List<ClassInfoDTO> getClassInfo(SearchCommand searchCommand);

    /**
     * 班级存在
     *
     * @param name        名字
     * @param instituteId 学院 ID
     * @param campusId    校区 ID
     * @return boolean
     */
    boolean existsByNameAndInstituteIdAndCampusId(String name, Long instituteId, Long campusId);
}
