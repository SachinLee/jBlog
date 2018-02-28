package cn.sachin.jaBlog.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 处理实体类中的异常信息，比如 字段超长
 */
public class ConstraintViolationExceptionHandler {

    /**
     * 批量获取异常信息
     * @param e
     * @return
     */
    public static String getMessage(ConstraintViolationException e) {
        List<String> msgList = Lists.newArrayList();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            msgList.add(constraintViolation.getMessage());
        }
        String messages = StringUtils.join(msgList.toArray(), ";");
        return messages;
    }
}
