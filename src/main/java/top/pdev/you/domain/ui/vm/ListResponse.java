package top.pdev.you.domain.ui.vm;

import lombok.Data;

import java.util.List;

/**
 * 简单列表值对象
 * Created in 2022/10/3 18:50
 *
 * @author Peter1303
 */
@Data
public class ListResponse<T> {
    private List<T> list;
}
