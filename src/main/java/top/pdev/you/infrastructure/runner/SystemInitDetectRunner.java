package top.pdev.you.infrastructure.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.pdev.you.common.enums.RedisKey;
import top.pdev.you.domain.repository.UserRepository;
import top.pdev.you.infrastructure.redis.RedisService;
import top.pdev.you.infrastructure.util.TagKeyUtil;

import javax.annotation.Resource;

/**
 * 系统初始化检测
 * Created in 2022/10/3 11:42
 *
 * @author Peter1303
 */
@Slf4j
@Order(4)
@Component
public class SystemInitDetectRunner implements ApplicationRunner {
    @Resource
    private RedisService redisService;

    @Resource
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.superAdminExists()) {
            redisService.set(TagKeyUtil.get(RedisKey.INIT), true);
        } else {
            log.warn("系统未完成初始化");
        }
    }
}
