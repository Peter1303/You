package top.pdev.you.domain.service.campus.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.domain.service.campus.CampusService;
import top.pdev.you.domain.ui.dto.CampusInfoDTO;
import top.pdev.you.domain.ui.vm.ListResponse;
import top.pdev.you.infrastructure.factory.CampusFactory;
import top.pdev.you.persistence.repository.CampusRepository;
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
    public void add(AddCampusCommand addCampusCommand) {
        Campus campus = campusFactory.newCampus();
        String name = addCampusCommand.getName();
        campus.setName(name);
        // 是否存在相同的
        if (campusRepository.existsByName(name)) {
            throw new BusinessException("已经存在相同的校区");
        }
        if (!campusRepository.save(campus)) {
            throw new BusinessException("保存校区失败");
        }
    }

    @Transactional
    @Override
    public void delete(IdCommand idCommand) {
        if (!campusRepository.removeById(idCommand.getId())) {
            throw new BusinessException("删除校区失败");
        }
    }

    @Override
    public ListResponse<CampusInfoDTO> getCampusList(SearchCommand searchCommand) {
        List<CampusInfoDTO> list = campusRepository.getCampusInfo(searchCommand.getName());
        ListResponse<CampusInfoDTO> listResponse = new ListResponse<>();
        listResponse.setList(list);
        return listResponse;
    }
}
