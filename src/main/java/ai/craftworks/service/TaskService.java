package ai.craftworks.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import ai.craftworks.mapper.TaskRowMapper;
import ai.craftworks.model.RequestTask;
import ai.craftworks.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Value("${sql.nextTask}")
    private String sqlNextTask;

    @Value("${sql.selectTask}")
    private String sqlSelectTask;

    @Value("${sql.allTasks}")
    private String sqlAllTasks;

    @Value("${sql.deleteTask}")
    private String sqlDeleteTask;

    @Value("${sql.updateTask}")
    private String sqlUpdateTask;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TaskUtil taskUtil;

    public void insertTask(RequestTask requestTask) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO \"Tasks\".\"Tasks\" VALUES (gen_random_uuid(), CURRENT_TIMESTAMP, null, ?, null,"
                        + "?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        //some validation will be needed
                        ps.setDate(1, java.sql.Date.valueOf(requestTask.getDueDate()));
                        ps.setString(2, requestTask.getTitle());
                        ps.setString(3, requestTask.getDescription());
                        ps.setInt(4, requestTask.getPriority());
                        ps.setBoolean(5, false);
                    }

                    @Override
                    public int getBatchSize() {
                        return 1;
                    }
                }
        );
    }

    public Optional<Task> getNextUnresolvedTask() {
        try {
            Task nextTask = jdbcTemplate.queryForObject(
                    sqlNextTask, new TaskRowMapper());
            return Optional.ofNullable(nextTask);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    private void setTaskExecuted(String id) {
        jdbcTemplate.batchUpdate(
                "UPDATE \"Tasks\".\"Tasks\" SET \"resolvedAt\"=?, \"status\"=? where \"id\"='" + id + "'",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                        ps.setBoolean(2, true);
                    }

                    @Override
                    public int getBatchSize() {
                        return 1;
                    }
                }
        );
    }

    public void executeTask(Task task) {
        System.out.println("Execute task " + task.getTitle());
        setTaskExecuted(task.getId());
    }

    public Task getTask(String id) {
        String query = String.format(sqlSelectTask, id);
        try {
            Task task = jdbcTemplate.queryForObject(
                    query, new TaskRowMapper());
            return task;
        } catch (Exception ex) {
            throw new RuntimeException("No task found for that id!");
        }
    }

    public List<Task> getAllTasks() {
        return jdbcTemplate.query(sqlAllTasks, new TaskRowMapper());
    }

    public void deleteTask(String id) {
        String query = String.format(sqlDeleteTask, id);
        jdbcTemplate.update(query);
    }

    public void updateTask(String id, RequestTask requestTask) {
        String updateClause = taskUtil.buildUpdateClause(requestTask);
        String updateQuery = String.format(sqlUpdateTask, updateClause, id);
        jdbcTemplate.update(updateQuery);
    }

}