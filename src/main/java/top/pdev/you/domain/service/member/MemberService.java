package top.pdev.you.domain.service.member;

import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.model.vm.StudentInfoResponse;
import top.pdev.you.web.command.IdCommand;

import java.util.List;

/**
 * 社员服务
 * Created in 2023/1/4 22:18
 *
 * @author Peter1303
 */
public interface MemberService {
    /**
     * 列表
     *
     * @param user 用户
     * @return {@link List}<{@link StudentInfoResponse}>
     */
    List<StudentInfoResponse> list(User user);

    /**
     * 删除
     *
     * @param user 用户
     * @param idCommand ID VO
     */
    void remove(User user, IdCommand idCommand);
}
