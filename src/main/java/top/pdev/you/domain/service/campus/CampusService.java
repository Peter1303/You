package top.pdev.you.domain.service.campus;

import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.campus.AddCampusCommand;
import top.pdev.you.domain.command.query.SearchCommand;
import top.pdev.you.domain.model.dto.CampusInfoDTO;
import top.pdev.you.domain.model.vm.ListResponse;
import top.pdev.you.infrastructure.result.Result;

/**
 * 校区服务
 * Created in 2022/11/1 19:01
 *
 * @author Peter1303
 */
public interface CampusService {
    /**
     * 获取校区列表
     *
     * @param searchCommand 搜索 VO
     * @return {@link Result}<{@link ?}>
     */
    ListResponse<CampusInfoDTO> getCampusList(SearchCommand searchCommand);

    /**
     * 添加
     *
     * @param addCampusCommand 添加校区 VO
     */
    void add(AddCampusCommand addCampusCommand);

    /**
     * 删除
     *
     * @param idCommand ID VO
     */
    void delete(IdCommand idCommand);
}
