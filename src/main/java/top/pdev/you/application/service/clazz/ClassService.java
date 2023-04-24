package top.pdev.you.application.service.clazz;

import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.web.clazz.command.AddClassCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.query.command.SearchCommand;

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
    Result<?> getClassList(SearchCommand vo);

    /**
     * 添加
     *
     * @param addClassCommand 添加班级 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> add(AddClassCommand addClassCommand);

    /**
     * 删除
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(IdCommand idCommand);
}
