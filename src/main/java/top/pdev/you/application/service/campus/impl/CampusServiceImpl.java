package top.pdev.you.application.service.campus.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.campus.CampusService;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.domain.factory.CampusFactory;
import top.pdev.you.domain.repository.CampusRepository;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.domain.ui.dto.CampusInfoDTO;
import top.pdev.you.domain.ui.ListVO;
import top.pdev.you.web.campus.command.AddCampusCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.query.command.SearchCommand;

import javax.annotation.Resource;
import java.util.List;

/**
 * 校区服务实现类
 * Created in 2022/11/1 19:02
 *
 * @author Peter1303
 */
@Service
public class CampusServiceImpl implements CampusService {
    @Resource
    private CampusRepository campusRepository;

    @Resource
    private CampusFactory campusFactory;

    @Transactional
    @Override
    public Result<?> add(AddCampusCommand addCampusCommand) {
        Campus campus = campusFactory.newCampus();
        campus.setName(addCampusCommand.getName());
        campus.save();
        return Result.ok();
    }

    @Transactional
    @Override
    public Result<?> delete(IdCommand idCommand) {
        if (!campusRepository.removeById(idCommand.getId())) {
            throw new BusinessException("删除校区失败");
        }
        return Result.ok();
    }

    @Override
    public Result<?> getCampusList(SearchCommand searchCommand) {
        List<CampusInfoDTO> list = campusRepository.getCampusInfo(searchCommand.getName());
        ListVO<CampusInfoDTO> listVO = new ListVO<>();
        listVO.setList(list);
        return Result.ok(listVO);
    }
}
