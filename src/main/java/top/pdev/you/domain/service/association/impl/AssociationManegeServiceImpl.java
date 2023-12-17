package top.pdev.you.domain.service.association.impl;

import org.springframework.stereotype.Service;
import top.pdev.you.common.entity.role.RoleEntity;
import top.pdev.you.common.enums.Permission;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.Association;
import top.pdev.you.domain.entity.AssociationManager;
import top.pdev.you.domain.entity.Manager;
import top.pdev.you.domain.entity.Student;
import top.pdev.you.domain.entity.Teacher;
import top.pdev.you.domain.entity.User;
import top.pdev.you.domain.service.association.AssociationManegeService;
import top.pdev.you.domain.service.user.UserService;
import top.pdev.you.persistence.repository.AssociationManagerRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 社团管理服务实现
 * Created in 2023/12/16 10:24
 *
 * @author Peter1303
 */
@Service
public class AssociationManegeServiceImpl implements AssociationManegeService {
    @Resource
    private UserService userService;

    @Resource
    private AssociationManagerRepository associationManagerRepository;

    @Override
    public void add(Association association, RoleEntity role) {
        AssociationManager associationManager = new AssociationManager();
        associationManager.setAssociationId(association.getId());
        User user = userService.getUser(role);
        Long userId = user.getId();
        associationManager.setUserId(userId);
        // 变更权限
        if (role instanceof Student) {
            // 负责人
            userService.permissionTo(user, Permission.MANAGER);
            associationManager.setType(Permission.MANAGER.getValue());
        } else if (role instanceof Teacher) {
            // 指导老师
            associationManager.setType(Permission.ADMIN.getValue());
        }
        if (!associationManagerRepository.save(associationManager)) {
            throw new BusinessException("无法保存社团管理人员");
        }
    }

    @Override
    public boolean exists(Association association, RoleEntity role) {
        User user = userService.getUser(role);
        return associationManagerRepository.existsByAssociationIdAndUserIdAndType(
                association.getId(),
                user.getId(),
                user.getPermission());
    }

    @Override
    public void remove(Association association, RoleEntity role) {
        User user = userService.getUser(role);
        Long userId = user.getId();
        Long associationId = Optional.ofNullable(association)
                .map(Association::getId)
                .orElse(null);
        if (role instanceof Manager) {
            // 找出负责人的管理社团
            List<AssociationManager> associationManagers =
                    associationManagerRepository.findByUserIdAndType(
                            userId,
                            Permission.MANAGER.getValue());
            AssociationManager manager = associationManagers.get(0);
            associationId = manager.getAssociationId();
        }
        if (!associationManagerRepository.deleteByAssociationAndUserId(
                associationId,
                userId)) {
            throw new BusinessException("无法销权");
        }
    }
}
