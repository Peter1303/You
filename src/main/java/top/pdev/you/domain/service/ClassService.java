package top.pdev.you.domain.service;

import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.NameVO;

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
    Result<?> getClassList(NameVO vo);
}
