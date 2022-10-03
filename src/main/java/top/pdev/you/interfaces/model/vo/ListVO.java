package top.pdev.you.interfaces.model.vo;

import lombok.Data;

import java.util.List;

/**
 * 简单列表 VO
 * Created in 2022/10/3 18:50
 *
 * @author Peter1303
 */
@Data
public class ListVO<T> {
    private List<T> list;
}
