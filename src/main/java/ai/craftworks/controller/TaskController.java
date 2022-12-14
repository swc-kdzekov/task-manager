package ai.craftworks.controller;

import java.util.List;
import java.util.Optional;

import ai.craftworks.model.RequestTask;
import ai.craftworks.model.Task;
import ai.craftworks.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public void createTask(@RequestBody RequestTask requestTask){
        logger.info("Create a task!");
        taskService.insertTask(requestTask);
    }

    @PutMapping("/update")
    public void updateTask(@RequestParam String id,
                           @RequestBody RequestTask requestTask){
        logger.info("Update a task!");
        taskService.updateTask(id, requestTask);
    }

    @DeleteMapping("/delete")
    public void deleteTask(@RequestParam String id){
        logger.info("Delete a task!");
        taskService.deleteTask(id);
    }

    @GetMapping("/fetch")
    public Task fetchTask(@RequestParam String id){
        logger.info("fetch a task!");
        return taskService.getTask(id);
    }

    @GetMapping("/fetchAll")
    public List<Task> fetchAllTask(){
        logger.info("fetch all tasks!");
        return taskService.getAllTasks();
    }

    /**
     * Select the next task. Execute it. Update its status.
     */
    @Scheduled(fixedRate = 15000)
    public void runIncomingTask() {
        Optional<Task> nextTask = taskService.getNextUnresolvedTask();
        nextTask.ifPresent(task -> taskService.executeTask(task));
    }
}
