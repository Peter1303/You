package top.pdev.you.domain.service.institute.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.command.institute.AddInstituteCommand;
import top.pdev.you.domain.command.query.SearchCommand;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.model.dto.InstituteInfoDTO;
import top.pdev.you.domain.model.vm.ListResponse;
import top.pdev.you.domain.service.institute.InstituteService;
import top.pdev.you.infrastructure.factory.InstituteFactory;
import top.pdev.you.persistence.repository.InstituteRepository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学院服务实现类
 * Created in 2022/11/1 17:07
 *
 * @author Peter1303
 */
@Service
public class InstituteServiceImpl implements InstituteService {
    @Resource
    private InstituteRepository instituteRepository;

    @Resource
    private InstituteFactory instituteFactory;

    /**
     * 添加
     *
     * @param addInstituteCommand 添加学院 VO
     */
    @Transactional
    @Override
    public void add(AddInstituteCommand addInstituteCommand) {
        Institute institute = instituteFactory.newInstitute();
        String name = addInstituteCommand.getName();
        institute.setName(name);
        // 检查存在
        if (instituteRepository.existsByName(name)) {
            throw new BusinessException("该学院已经存在");
        }
        if (!instituteRepository.save(institute)) {
            throw new BusinessException("保存学院失败");
        }
    }

    /**
     * 删除
     *
     * @param idCommand ID VO
     */
    @Transactional
    @Override
    public void delete(IdCommand idCommand) {
        if (!instituteRepository.removeById(idCommand.getId())) {
            throw new BusinessException("删除学院失败");
        }
    }

    /**
     * 获取学院列表
     *
     * @param searchCommand 搜索 VO
     */
    @Override
    public ListResponse<InstituteInfoDTO> getInstituteList(SearchCommand searchCommand) {
        List<InstituteInfoDTO> list = instituteRepository.getInstituteInfo(searchCommand.getName());
        ListResponse<InstituteInfoDTO> listResponse = new ListResponse<>();
        listResponse.setList(list);
        return listResponse;
    }
}
