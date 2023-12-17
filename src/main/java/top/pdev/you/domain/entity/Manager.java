package top.pdev.you.domain.entity;

/**
 * 负责人领域
 * Created in 2023/1/2 22:46
 *
 * @author Peter1303
 */
public class Manager extends Student {
    public Manager(Student student) {
        setId(student.getId());
        setUserId(student.getUserId());
        setClassId(student.getClassId());
        setNo(student.getNo());
        setName(student.getName());
        setContact(student.getContact());
    }
}
