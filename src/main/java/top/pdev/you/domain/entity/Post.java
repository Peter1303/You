package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import top.pdev.you.common.entity.role.ManagerEntity;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.entity.role.SuperEntity;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.PermissionDeniedException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.entity.data.PostDO;
import top.pdev.you.domain.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 帖子领域
 * Created in 2023/1/2 15:18
 *
 * @author Peter1303
 */
@Data
public class Post extends BaseEntity {
    private Long id;
    private Long userId;
    private Long associationId;
    private String content;
    private LocalDateTime time;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private PostDO postDO;

    public Post(PostDO postDO) {
        if (!Optional.ofNullable(postDO).isPresent()) {
            return;
        }
        this.postDO = postDO;
        this.id = postDO.getId();
        this.associationId = postDO.getAssociationId();
        this.userId = postDO.getUserId();
        this.content = postDO.getContent();
        this.time = postDO.getTime();
    }

    /**
     * 保存
     */
    public void save() {
        postDO = new PostDO();
        postDO.setAssociationId(associationId);
        postDO.setContent(content);
        postDO.setUserId(userId);
        postDO.setTime(LocalDateTime.now());
        PostRepository postRepository = SpringUtil.getBean(PostRepository.class);
        if (!postRepository.save(postDO)) {
            throw new BusinessException("无法发布帖子");
        }
    }

    /**
     * 删除
     *
     * @param user 用户
     */
    public void delete(User user) {
        boolean hasPermission = false;
        // 是否为自己的帖子
        if (Objects.equals(user.getId(), this.userId)) {
            hasPermission = true;
        } else {
            RoleEntity role = user.getRoleDomain();
            // 如果是超级管理员、负责人也有权限
            if (role instanceof ManagerEntity || role instanceof SuperEntity) {
                hasPermission = true;
            }
        }
        if (!hasPermission) {
            throw new PermissionDeniedException();
        }
        PostRepository postRepository = SpringUtil.getBean(PostRepository.class);
        if (!postRepository.removeById(this.id)) {
            throw new BusinessException("无法删除帖子");
        }
    }

    /**
     * 改变内容
     *
     * @param content 内容
     */
    public void changeContent(String content) {
        this.content = content;
        PostRepository postRepository = SpringUtil.getBean(PostRepository.class);
        if (!postRepository.saveOrUpdate(postDO)) {
            throw new BusinessException("无法更改内容");
        }
    }
}
