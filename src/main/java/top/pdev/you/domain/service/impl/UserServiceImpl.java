package top.pdev.you.domain.service.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.application.service.WechatService;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.domain.service.UserService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.infrastructure.result.ResultCode;
import top.pdev.you.interfaces.dto.WechatLoginDTO;
import top.pdev.you.interfaces.model.vo.LoginResultVO;
import top.pdev.you.interfaces.model.vo.UserLoginVO;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户服务实现类
 * Created in 2022/10/2 17:56
 *
 * @author Peter1303
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private WechatService wechatService;

    @Resource
    private UserRepository userRepository;

    @Override
    public Result<?> login(UserLoginVO vo) {
        WechatLoginDTO dto = wechatService.login(vo.getCode());
        if (Optional.ofNullable(dto).isPresent()) {
            String openId = dto.getOpenId();
            if (Optional.ofNullable(openId).isPresent()) {
                User user = userRepository.findByOpenId(openId);
                if (Optional.ofNullable(user).isPresent()) {
                    LoginResultVO loginResultVO = new LoginResultVO();
                    loginResultVO.setToken(openId);
                    return Result.ok().setData(loginResultVO);
                }
                throw new BusinessException(ResultCode.NOT_REGISTERED);
            }
        }
        throw new BusinessException(ResultCode.FAILED, "登录失败");
    }
}
