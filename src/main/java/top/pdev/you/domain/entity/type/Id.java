package top.pdev.you.domain.entity.type;

import lombok.Getter;

/**
 * ID
 * Created in 2022/10/1 16:07
 *
 * @author Peter1303
 */
public class Id {
    @Getter
    private final Long id;

    public Id(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
