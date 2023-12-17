package top.pdev.you.domain.service.clazz;

import top.pdev.you.domain.ui.dto.ClassInfoDTO;
import top.pdev.you.domain.ui.vm.ListResponse;
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
    ListResponse<ClassInfoDTO> getClassList(SearchCommand vo);

    /**
     * 添加
     *
     * @param addClassCommand 添加班级 VO
     */
    void add(AddClassCommand addClassCommand);

    /**
     * 删除
     *
     * @param idCommand ID VO
     */
    void delete(IdCommand idCommand);
}
