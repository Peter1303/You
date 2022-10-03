package top.pdev.you.domain.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.UserDO;
import top.pdev.you.domain.entity.types.UserId;
import top.pdev.you.domain.repository.UserRepository;

import java.util.Optional;

/**
 * 用户领域
 * Created in 2022/9/30 22:32
 *
 * @author Peter1303
 */
@Data
public class User extends BaseEntity {
    private UserId userId;
    private String openId;
    private Long targetId;
    private Integer permission;

    private final UserRepository userRepository = SpringUtil.getBean(UserRepository.class);

    public User(UserDO userDO) {
        if (!Optional.ofNullable(userDO).isPresent()) {
            return;
        }
        this.userId = new UserId(userDO.getId());
        this.openId = userDO.getWechatId();
        if (Optional.ofNullable(targetId).isPresent()) {
            this.targetId = userDO.getTargetId();
        }
        this.permission = userDO.getPermission();
    }

    /**
     * 保存
     *
     * @param userDO 用户 DO
     */
    public void save(UserDO userDO) {
        if (!userRepository.save(userDO)) {
            throw new InternalErrorException("无法保存用户");
        }
    }

    /**
     * 保存
     *
     * @param teacher 老师
     */
    public void save(Teacher teacher) {
        UserDO userDO = new UserDO();
        userDO.setWechatId(openId);
        userDO.setTime(DateTime.now().toLocalDateTime());
        userDO.setTargetId(teacher.getId());
        userDO.setPermission(Permission.USER.getValue());
        save(userDO);
    }
}
