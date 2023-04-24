package top.pdev.you.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.ui.dto.InstituteInfoDTO;

import java.util.List;

/**
 * 学院仓库
 * Created in 2022/10/26 11:38
 *
 * @author Peter1303
 */
public interface InstituteRepository extends IService<Institute> {
    /**
     * 通过 ID 查找
     *
     * @param instituteId 研究所 ID
     * @return {@link Institute}
     */
    Institute findById(Long instituteId);

    /**
     * 获取学院信息
     *
     * @param name 名字
     * @return {@link List}<{@link InstituteInfoDTO}>
     */
    List<InstituteInfoDTO> getInstituteInfo(String name);

    /**
     * 存在
     *
     * @param instituteId 学院 ID
     * @return boolean
     */
    boolean existsById(Long instituteId);

    /**
     * 存在
     *
     * @param name 名字
     * @return boolean
     */
    boolean existsByName(String name);
}
