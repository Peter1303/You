package top.pdev.you.domain.entity.types;

import lombok.Data;
import top.pdev.you.common.exception.InternalErrorException;

import java.util.Optional;

/**
 * ID
 * Created in 2022/10/1 16:07
 *
 * @author Peter1303
 */
@Data
public class Id {
    private Long id;

    public void init(Long id, boolean check) {
        if (check) {
            Optional.ofNullable(id)
                    .orElseThrow(() -> new InternalErrorException("ID 不可为空"));
        }
        this.id = id;
    }

    public Id(Long id) {
        init(id, true);
    }

    public Id(Long id, boolean check) {
        init(id, check);
    }
}
