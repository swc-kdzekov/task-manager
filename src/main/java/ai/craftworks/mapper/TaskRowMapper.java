package ai.craftworks.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ai.craftworks.model.Task;
import org.springframework.jdbc.core.RowMapper;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getString("id"));
        task.setCreatedAt(rs.getTimestamp("createdAt"));
        task.setUpdatedAt(rs.getTimestamp("updatedAt"));
        task.setDueDate(rs.getDate("dueDate"));
        task.setResolvedAt(rs.getTimestamp("resolvedAt"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setPriority(rs.getInt("priority"));
        task.setStatus(rs.getBoolean("status"));
        return task;
    }
}
