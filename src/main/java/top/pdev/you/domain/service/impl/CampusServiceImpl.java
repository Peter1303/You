package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Campus;
import top.pdev.you.domain.entity.data.CampusDO;
import top.pdev.you.domain.factory.CampusFactory;
import top.pdev.you.domain.repository.CampusRepository;
import top.pdev.you.domain.service.CampusService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.AddCampusVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;

import javax.annotation.Resource;

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

    @Override
    public Result<?> add(AddCampusVO addCampusVO) {
        Campus campus = campusFactory.newCampus();
        CampusDO campusDO = new CampusDO();
        campusDO.setName(addCampusVO.getName());
        campus.save(campusDO);
        return Result.ok();
    }

    @Override
    public Result<?> delete(IdVO idVO) {
        if (!campusRepository.removeById(idVO.getId())) {
            throw new BusinessException("删除校区失败");
        }
        return Result.ok();
    }
}
