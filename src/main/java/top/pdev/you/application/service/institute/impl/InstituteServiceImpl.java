package top.pdev.you.application.service.institute.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.pdev.you.application.service.institute.InstituteService;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.factory.InstituteFactory;
import top.pdev.you.domain.repository.InstituteRepository;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.domain.ui.dto.InstituteInfoDTO;
import top.pdev.you.domain.ui.ListVO;
import top.pdev.you.web.institute.command.AddInstituteCommand;
import top.pdev.you.web.command.IdCommand;
import top.pdev.you.web.query.command.SearchCommand;

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
     * @return {@link Result}<{@link ?}>
     */
    @Transactional
    @Override
    public Result<?> add(AddInstituteCommand addInstituteCommand) {
        Institute institute = instituteFactory.newInstitute();
        institute.setName(addInstituteCommand.getName());
        institute.save();
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param idCommand ID VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional
    @Override
    public Result<?> delete(IdCommand idCommand) {
        if (!instituteRepository.removeById(idCommand.getId())) {
            throw new BusinessException("删除学院失败");
        }
        return Result.ok();
    }

    /**
     * 获取学院列表
     *
     * @param searchCommand 搜索 VO
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> getInstituteList(SearchCommand searchCommand) {
        List<InstituteInfoDTO> list = instituteRepository.getInstituteInfo(searchCommand.getName());
        ListVO<InstituteInfoDTO> listVO = new ListVO<>();
        listVO.setList(list);
        return Result.ok(listVO);
    }
}