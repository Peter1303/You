package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Institute;
import top.pdev.you.domain.entity.data.InstituteDO;
import top.pdev.you.domain.factory.InstituteFactory;
import top.pdev.you.domain.repository.InstituteRepository;
import top.pdev.you.domain.service.InstituteService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddInstituteVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;

import javax.annotation.Resource;

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
     * @param addInstituteVO 添加学院 VO
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> add(AddInstituteVO addInstituteVO) {
        Institute institute = instituteFactory.newInstitute();
        InstituteDO instituteDO = new InstituteDO();
        instituteDO.setName(addInstituteVO.getName());
        institute.save(instituteDO);
        return Result.ok();
    }

    /**
     * 删除
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> delete(IdVO idVO) {
        if (!instituteRepository.removeById(idVO.getId())) {
            throw new BusinessException("删除学院失败");
        }
        return Result.ok();
    }
}
