package top.pdev.you.domain.service;

import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddClassVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
import top.pdev.you.interfaces.model.vo.req.SearchVO;

/**
 * 班级服务
 * Created in 2022/10/3 18:28
 *
 * @author Peter1303
 */
public interface ClassService {
    /**
     * 获取班级列表
     *
     * @param vo VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> getClassList(SearchVO vo);

    /**
     * 添加
     *
     * @param addClassVO 添加班级 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(AddClassVO addClassVO);

    /**
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdVO idVO);
}
