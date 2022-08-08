package ai.craftworks.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import ai.craftworks.mapper.TaskRowMapper;
import ai.craftworks.model.RequestTask;
import ai.craftworks.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertTask(RequestTask requestTask){
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

    public Task queryForTask(String query){
        return jdbcTemplate.queryForObject(query, new TaskRowMapper());
    }

    public void setTaskExecuted(String id) {
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

    public List<Task> queryTasks(String query){
        return jdbcTemplate.query(query, new TaskRowMapper());
    }

    public void updateTask(String query){
        jdbcTemplate.update(query);
    }
}
