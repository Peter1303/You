package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.domain.repository.AssociationManagerRepository;

import java.util.List;

/**
 * 负责人领域
 * Created in 2023/1/2 22:46
 *
 * @author Peter1303
 */
public class Manager extends Student {
    public Manager(User user) {
        super(user);
    }

    /**
     * 归属社团
     *
     * @return {@link Association}
     */
    public Association belongAssociation() {
        AssociationManagerRepository associationManagerRepository =
                SpringUtil.getBean(AssociationManagerRepository.class);
        List<AssociationManager> managers = associationManagerRepository.findByUserIdAndType(getUserId(),
                Permission.MANAGER.getValue());
        AssociationManager manager = managers.get(0);
        return manager.getAssociation();
    }
}
