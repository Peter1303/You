package top.pdev.you.domain.service;

import top.pdev.you.common.entity.TokenInfo;
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
     * @param postVO    帖子 VO
     * @param tokenInfo 令牌信息
     * @return {@link Result}<{@link ?}>
     */
    Result<?> post(TokenInfo tokenInfo, PostVO postVO);

    /**
     * 发布社团帖子
     *
     * @param tokenInfo 令牌信息
     * @param postVO    帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> associationPost(TokenInfo tokenInfo, PostVO postVO);

    /**
     * 细节
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> details(IdVO idVO);

    /**
     * 列表
     *
     * @param postListVO 帖子列表 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> list(PostListVO postListVO);

    /**
     * 删除
     *
     * @param tokenInfo 令牌信息
     * @param idVO      ID VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> delete(TokenInfo tokenInfo, IdVO idVO);

    /**
     * 修改帖子
     *
     * @param changePostVO 修改帖子 VO
     * @return {@link Result}<{@link ?}>
     */
    Result<?> changePost(ChangePostVO changePostVO);
}
