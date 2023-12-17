package top.pdev.you.domain.service.like;

import top.pdev.you.domain.command.IdCommand;
import top.pdev.you.domain.entity.User;

/**
 * 点赞服务
 * Created in 2023/1/2 19:13
 *
 * @author Peter1303
 */
public interface LikeService {
    /**
     * 添加
     *
     * @param user 用户
     * @param idCommand ID VO
     */
    void add(User user, IdCommand idCommand);

    /**
     * 删除
     *
     * @param user 用户
     * @param idCommand ID VO
     */
    void delete(User user, IdCommand idCommand);
}
