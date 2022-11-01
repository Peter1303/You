package top.pdev.you.domain.service;

import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddCampusVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;

/**
 * 校区服务
 * Created in 2022/11/1 19:01
 *
 * @author Peter1303
 */
public interface CampusService {
    /**
     * 添加
     *
     * @param addCampusVO 添加校区 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(AddCampusVO addCampusVO);

    /**
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdVO idVO);
}
