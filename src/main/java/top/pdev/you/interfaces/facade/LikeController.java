package top.pdev.you.interfaces.facade;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.pdev.you.common.annotation.CurrentUser;
import top.pdev.you.common.entity.TokenInfo;
import top.pdev.you.domain.service.LikeService;
import top.pdev.you.infrastructure.result.Result;
import top.pdev.you.interfaces.model.vo.req.IdVO;

import javax.annotation.Resource;

/**
 * 点赞粗粒度
 * Created in 2023/1/2 19:11
 *
 * @author Peter1303
 */
@RequestMapping("/like")
@RestController
public class LikeController {
    @Resource
    private LikeService likeService;

    /**
     * 添加
     *
     * @param tokenInfo 令牌信息
     * @param idVO      ID VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public Result<?> add(@CurrentUser TokenInfo tokenInfo,
                         @RequestBody @Validated IdVO idVO) {
        return likeService.add(tokenInfo, idVO);
    }

    /**
     * 取消
     *
     * @param idVO ID VO
     * @return {@link Result}<{@link ?}>
     */
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("")
    public Result<?> cancel(@RequestBody @Validated IdVO idVO) {
        return likeService.delete(idVO);
    }
}
