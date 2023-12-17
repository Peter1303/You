package top.pdev.you.domain.service.clazz;

import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.clazz.AddClassCommand;
import top.pdev.you.domain.command.query.SearchCommand;
import top.pdev.you.domain.model.dto.ClassInfoDTO;
import top.pdev.you.domain.model.vm.ListResponse;
import top.pdev.you.infrastructure.result.Result;

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
