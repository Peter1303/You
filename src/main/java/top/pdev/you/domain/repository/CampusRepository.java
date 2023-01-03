package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.interfaces.model.dto.CampusInfoDTO;

import java.util.List;

/**
 * 校区仓库
 * Created in 2022/10/26 11:13
 *
 * @author Peter1303
 */
public interface CampusRepository extends IService<Campus> {
    /**
     * 通过 ID 查找
     *
     * @param id ID
     * @return {@link Campus}
     */
    Campus findById(Long id);

    /**
     * 获取校区信息
     *
     * @param name 名字
     * @return {@link List}<{@link CampusInfoDTO}>
     */
    List<CampusInfoDTO> getCampusInfo(String name);

    /**
     * 存在
     *
     * @param campusId 校区 ID
     * @return boolean
     */
    boolean existsById(Long campusId);

    /**
     * 存在
     *
     * @param name 名字
     * @return boolean
     */
    boolean existsByName(String name);
}
