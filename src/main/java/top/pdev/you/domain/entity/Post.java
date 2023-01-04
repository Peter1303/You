package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.entity.role.SuperEntity;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.common.exception.PermissionDeniedException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 帖子领域
 * Created in 2023/1/2 15:18
 *
 * @author Peter1303
 */
@TableName("post")
@Data
public class Post extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 社团 ID
     */
    private Long associationId;

    /**
     * 用户 ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 保存
     */
    public void save() {
        PostRepository postRepository = SpringUtil.getBean(PostRepository.class);
        if (!postRepository.save(this)) {
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
            if (role instanceof Manager || role instanceof SuperEntity) {
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
        Post post = new Post();
        post.setId(id);
        post.setContent(content);
        PostRepository postRepository = SpringUtil.getBean(PostRepository.class);
        if (!postRepository.saveOrUpdate(post)) {
            throw new BusinessException("无法更改内容");
        }
    }
}
