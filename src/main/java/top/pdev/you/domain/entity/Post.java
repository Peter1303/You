package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.domain.repository.PostRepository;

import java.time.LocalDateTime;

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
     */
    public void delete() {
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
