package top.pdev.you.domain.entity;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.pdev.you.common.constant.ActivityRule;
import top.pdev.you.common.constant.ActivityTarget;
import top.pdev.you.common.exception.BusinessException;
import top.pdev.you.domain.entity.base.BaseEntity;
import top.pdev.you.web.activity.command.RuleCommand;
import top.pdev.you.web.command.TimeCommand;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动
 * Created in 2022/10/1 15:24
 *
 * @author Peter1303
 */
@TableName("activity")
@Data
public class Activity extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("association_id")
    private Long associationId;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String summary;

    /**
     * 细节
     */
    private String detail;

    /**
     * 位置
     */
    private String location;

    /**
     * 活动时间
     */
    private LocalDateTime time;

    public List<Rule> rules(RuleCommand command) {
        notNull(Activity::getId);
        Integer target = command.getTarget();
        TimeCommand timeCommand = command.getTimeCommand();
        String startTime = timeCommand.getStartTime();
        String endTime = timeCommand.getEndTime();
        try {
            // 解析时间
            DateTime start = DateUtil.parse(startTime);
            DateTime end = DateUtil.parse(endTime);
            if (start == null
                    || end == null
                    || start.isBefore(DateTime.now())
                    || end.isBefore(DateTime.now())) {
                throw new BusinessException("时间错误");
            }
            // 解析对象
            int grade = target;
            if (target >= ActivityTarget.TEACHER_FLAG) {
                grade = target - ActivityTarget.TEACHER_FLAG;
            }
            boolean teacher = (target & ActivityTarget.TEACHER_FLAG) > 0;
            List<Rule> rules = new ArrayList<>();

            Activity.Rule rule = new Activity.Rule();
            rule.setActivityId(this.getId());
            rule.setRule(ActivityRule.GRADE);
            rule.setValue(String.valueOf(grade));
            rules.add(rule);

            String startTimeString = start.toString();
            String endTimeString = end.toString();
            rule = new Activity.Rule();
            rule.setActivityId(this.getId());
            rule.setRule(ActivityRule.START_TIME);
            rule.setValue(startTimeString);
            rules.add(rule);
            rule = new Activity.Rule();
            rule.setActivityId(this.getId());
            rule.setRule(ActivityRule.END_TIME);
            rule.setValue(endTimeString);
            rules.add(rule);

            rule = new Activity.Rule();
            rule.setActivityId(this.getId());
            rule.setRule(ActivityRule.TEACHER);
            rule.setValue(String.valueOf(teacher ? 1 : 0));
            rules.add(rule);

            rule = new Activity.Rule();
            rule.setActivityId(this.getId());
            rule.setRule(ActivityRule.TOTAL);
            rule.setValue(String.valueOf(command.getTotal()));
            rules.add(rule);
            return rules;
        } catch (DateException ignored) {
            throw new BusinessException("时间错误");
        }
    }

    @TableName("activity_rule")
    @Data
    public static class Rule {
        @TableId(type = IdType.AUTO)
        private Long id;

        @TableField("activity_id")
        private Long activityId;

        private Integer rule;

        private String value;

        public void validate(Object value) {
            switch (rule) {
                case ActivityRule.TEACHER:
                    // 没有允许老师参加
                    if (this.value.equals("0")) {
                        // 然而是老师
                        if ("1".equals(value)) {
                            throw new BusinessException("不允许老师参与");
                        }
                    }
                    break;
                case ActivityRule.TOTAL:
                    // 如果活动达到了最大
                    if (Integer.parseInt(this.value) == (Integer) value) {
                        throw new BusinessException("活动人数已满");
                    }
                    break;
                case ActivityRule.GRADE:
                    int targetGrade = Integer.parseInt(this.value);
                    // 如果有年级限制
                    if (targetGrade != 0) {
                        if (targetGrade != (Integer) value) {
                            throw new BusinessException("只允许 " + targetGrade + " 年级参加");
                        }
                    }
                    break;
                case ActivityRule.END_TIME:
                    DateTime end = DateUtil.parse(this.value);
                    if (end != null) {
                        String dateString = (String) value;
                        if (end.before(DateUtil.parse(dateString))) {
                            throw new BusinessException("你错过活动时间了");
                        }
                    }
                    break;
            }
        }
    }
}
