package top.pdev.you;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(SpringUtil.class)
@SpringBootApplication
public class YouApplication {
    public static void main(String[] args) {
        SpringApplication.run(YouApplication.class, args);
    }
}
