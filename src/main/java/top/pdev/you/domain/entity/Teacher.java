package top.pdev.you.domain.entity;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Data;
import top.pdev.you.common.exception.InternalErrorException;
import top.pdev.you.domain.entity.data.TeacherDO;
import top.pdev.you.domain.entity.types.TeacherId;
import top.pdev.you.domain.repository.TeacherRepository;

import java.util.Optional;

/**
 * 老师领域
 * Created in 2022/10/3 16:24
 *
 * @author Peter1303
 */
@Data
public class Teacher {
    private User user;
    private TeacherId teacherId;
    private String name;
    private String no;
    private String contact;

    private final TeacherRepository teacherRepository = SpringUtil.getBean(TeacherRepository.class);

    public Teacher(User user) {
        if (!Optional.ofNullable(user).isPresent()) {
            return;
        }
        this.user = user;
        this.teacherId = new TeacherId(user.getTargetId());
        TeacherDO teacherDO = teacherRepository.getDO(teacherId);
        this.name = teacherDO.getName();
        this.no = teacherDO.getNo();
        this.contact = teacherDO.getContact();
    }

    /**
     * 保存
     *
     * @param teacherDO 老师 DO
     */
    public void save(TeacherDO teacherDO) {
        if (!teacherRepository.save(teacherDO)) {
            throw new InternalErrorException("无法保存老师");
        }
        this.teacherId = new TeacherId(teacherDO.getId());
    }
}
