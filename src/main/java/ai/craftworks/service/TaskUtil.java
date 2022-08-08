package ai.craftworks.service;

import java.sql.Timestamp;

import ai.craftworks.model.RequestTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TaskUtil {

    public String buildUpdateClause(RequestTask requestTask) {
        StringBuilder sb = new StringBuilder();

        //Validate date format
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
}
