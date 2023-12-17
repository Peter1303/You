package top.pdev.you.domain.service.institute;

import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.institute.AddInstituteCommand;
import top.pdev.you.domain.command.query.SearchCommand;
import top.pdev.you.domain.model.dto.InstituteInfoDTO;
import top.pdev.you.domain.model.vm.ListResponse;

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
     * @param addInstituteCommand 添加学院 VO
     */
    void add(AddInstituteCommand addInstituteCommand);

    /**
     * 删除
     *
     * @param idCommand ID VO
     */
    void delete(IdCommand idCommand);

    /**
     * 获取学院列表
     *
     * @param searchCommand 搜索 VO
     */
    ListResponse<InstituteInfoDTO> getInstituteList(SearchCommand searchCommand);
}
