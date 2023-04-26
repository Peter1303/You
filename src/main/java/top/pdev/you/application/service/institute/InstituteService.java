package top.pdev.you.application.service.institute;

import top.pdev.you.domain.ui.dto.InstituteInfoDTO;
import top.pdev.you.domain.ui.vm.ListResponse;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.institute.command.AddInstituteCommand;
import top.pdev.you.web.query.command.SearchCommand;

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
