package ai.craftworks.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ai.craftworks.exceptions.DateFormatException;
import ai.craftworks.model.RequestTask;
import org.apache.commons.lang3.StringUtils;

public class TaskUtil {

    public static String buildUpdateClause(RequestTask requestTask) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(requestTask.getDueDate())) {
            sb.append("\"dueDate\"='" + requestTask.getDueDate() + "', ");
        }

        if (StringUtils.isNotEmpty(requestTask.getTitle())) {
            sb.append("\"title\"='" + requestTask.getTitle() + "', ");
        }

        if (StringUtils.isNotEmpty(requestTask.getTitle())) {
            sb.append("\"description\"='" + requestTask.getDescription() + "', ");
        }

        if (StringUtils.isNotEmpty(requestTask.getPriority().toString())) {
            sb.append("\"priority\"='" + requestTask.getPriority() + "', ");
        }

        sb.append("\"updatedAt\"='" + new Timestamp(System.currentTimeMillis()) + "'");

        return sb.toString();
    }

    public static void validateTaskRequest(RequestTask requestTask){
        if (StringUtils.isNotEmpty(requestTask.getDueDate())) {
            validateDate(requestTask.getDueDate());
        }
    }

    private static void validateDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            throw new DateFormatException("Incorrect date format. It should comply with: [yyyy-MM-dd]!");
        }
    }

}
