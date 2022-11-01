package top.pdev.you.domain.service;

import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddInstituteVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;

/**
 * 学院服务
 * Created in 2022/11/1 17:07
 *
 * @author Peter1303
 */
public interface InstituteService {
    /**
     * 添加
     *
     * @param addInstituteVO 添加学院 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(AddInstituteVO addInstituteVO);

    /**
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdVO idVO);
}
