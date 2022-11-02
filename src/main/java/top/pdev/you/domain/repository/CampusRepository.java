package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.data.CampusDO;
import top.pdev.you.domain.entity.types.Id;
import top.pdev.you.interfaces.model.dto.CampusInfoDTO;

import java.util.List;

/**
 * 校区仓库
 * Created in 2022/10/26 11:13
 *
 * @author Peter1303
 */
public interface CampusRepository extends IService<CampusDO> {
    /**
     * 获取校区名字
     *
     * @param id ID
     * @return {@link String}
     */
    String getName(Id id);

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
    boolean exists(Long campusId);

    /**
     * 存在
     *
     * @param name 名字
     * @return boolean
     */
    boolean exists(String name);
}
