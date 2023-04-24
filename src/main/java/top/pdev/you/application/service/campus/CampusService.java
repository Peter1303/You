package top.pdev.you.application.service.campus;

import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.campus.command.AddCampusCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.query.command.SearchCommand;

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
     * @param addCampusCommand 添加校区 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(AddCampusCommand addCampusCommand);

    /**
     * 删除
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdCommand idCommand);

    /**
     * 获取校区列表
     *
     * @param searchCommand 搜索 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> getCampusList(SearchCommand searchCommand);
}