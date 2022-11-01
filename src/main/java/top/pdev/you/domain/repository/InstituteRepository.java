package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.data.InstituteDO;
import top.pdev.you.domain.entity.types.Id;

/**
 * 学院仓库
 * Created in 2022/10/26 11:38
 *
 * @author Peter1303
 */
public interface InstituteRepository extends IService<InstituteDO> {
    /**
     * 获取名字
     *
     * @param id ID
     * @return {@link String}
     */
    String getName(Id id);

    /**
     * 存在
     *
     * @param instituteId 学院 ID
     * @return boolean
     */
    boolean exists(Long instituteId);
}
