package ai.craftworks.service;

import java.util.List;
import java.util.Optional;

import ai.craftworks.exceptions.NoTaskFoundException;
import ai.craftworks.model.RequestTask;
import ai.craftworks.model.Task;
import ai.craftworks.util.TaskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

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
    private TaskDao taskDao;

    public void insertTask(RequestTask requestTask) {
        TaskUtil.validateTaskRequest(requestTask);
        taskDao.insertTask(requestTask);
    }

    public Optional<Task> getNextUnresolvedTask() {
        try {
            Task nextTask = taskDao.queryForTask(sqlNextTask);
            return Optional.of(nextTask);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public void executeTask(Task task) {
        taskDao.setTaskExecuted(task.getId());
        logger.info(String.format("Execute task: '%s'", task.getTitle()));
    }

    public Task getTask(String id) {
        String query = String.format(sqlSelectTask, id);
        try {
            return taskDao.queryForTask(query);
        } catch (Exception ex) {
            throw new NoTaskFoundException(String.format("No task found for id: '%s'", id));
        }
    }

    public List<Task> getAllTasks() {
        return taskDao.queryTasks(sqlAllTasks);
    }

    public void deleteTask(String id) {
        String query = String.format(sqlDeleteTask, id);
        taskDao.updateTask(query);
    }

    public void updateTask(String id, RequestTask requestTask) {
        TaskUtil.validateTaskRequest(requestTask);
        String updateClause = TaskUtil.buildUpdateClause(requestTask);
        String updateQuery = String.format(sqlUpdateTask, updateClause, id);
        taskDao.updateTask(updateQuery);
    }
}