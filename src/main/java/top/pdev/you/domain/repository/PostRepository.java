package top.pdev.you.domain.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.pdev.you.domain.entity.Post;

import java.util.List;

/**
 * 帖子仓库
 * Created in 2023/1/2 15:13
 *
 * @author Peter1303
 */
public interface PostRepository extends IService<Post> {
    /**
     * 通过 ID 查找
     *
     * @param id ID
     * @return {@link Post}
     */
    Post findById(Long id);

    /**
     * 帖子存在
     *
     * @param id ID
     * @return {@link Boolean}
     */
    Boolean existsById(Long id);

    /**
     * 通过社团 ID 获取列表
     *
     * @param associationId 社团 ID
     * @return {@link List}<{@link Post}>
     */
    List<Post> findByAssociationId(Long associationId);


    /**
     * 通过用户 ID 查找
     *
     * @param userId 用户 ID
     * @return {@link List}<{@link Post}>
     */
    List<Post> findByUserId(Long userId);
}
