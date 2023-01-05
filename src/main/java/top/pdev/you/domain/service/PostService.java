package top.pdev.you.domain.service;

import top.pdev.you.domain.entity.User;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.ChangePostVO;
import top.pdev.you.interfaces.model.vo.req.IdVO;
import top.pdev.you.interfaces.model.vo.req.PostListVO;
import top.pdev.you.interfaces.model.vo.req.PostVO;

/**
 * 帖子服务
 * Created in 2023/1/2 13:35
 *
 * @author Peter1303
 */
public interface PostService {
    /**
     * 发布帖子
     *
     * @param user   用户
     * @param postVO 帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> post(User user, PostVO postVO);

    /**
     * 发布社团帖子
     *
     * @param user   用户
     * @param postVO 帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> associationPost(User user, PostVO postVO);

    /**
     * 细节
     *
     * @param user 用户
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> details(User user, IdVO idVO);

    /**
     * 列表
     *
     * @param user       用户
     * @param postListVO 帖子列表 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> list(User user, PostListVO postListVO);

    /**
     * 用户帖子列表
     *
     * @param user 用户
     * @return {@link Result}<{@link ?}>
     */
    Result<?> listOfUser(User user);

    /**
     * 删除
     *
     * @param user 用户
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(User user, IdVO idVO);

    /**
     * 修改帖子
     *
     * @param changePostVO 修改帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> changePost(ChangePostVO changePostVO);
}
