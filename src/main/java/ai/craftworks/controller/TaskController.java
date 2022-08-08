package ai.craftworks.controller;

import java.util.List;
import java.util.Optional;

import ai.craftworks.model.RequestTask;
import ai.craftworks.model.Task;
import ai.craftworks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public void createTask(@RequestBody RequestTask requestTask){
        System.out.println("Create a task!");
        taskService.insertTask(requestTask);
    }

    @PutMapping("/update")
    public void updateTask(@RequestParam String id,
                           @RequestBody RequestTask requestTask){
        System.out.println("Update a task!");
        taskService.updateTask(id, requestTask);
    }

    @DeleteMapping("/delete")
    public void deleteTask(@RequestParam String id){
        System.out.println("Delete a task!");
        taskService.deleteTask(id);
    }

    @GetMapping("/fetch")
    public Task fetchTask(@RequestParam String id){
        System.out.println("fetch a task!");
        return taskService.getTask(id);
    }

    @GetMapping("/fetchAll")
    public List<Task> fetchAllTask(){
        System.out.println("fetch all tasks!");
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
