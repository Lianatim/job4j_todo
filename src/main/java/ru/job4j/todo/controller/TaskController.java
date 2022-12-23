package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            model.addAttribute("message", "Add new task :)");
        }
        model.addAttribute("tasks", tasks);
        return "task/tasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model) {
        return "task/add";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/formTaskInfo/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", taskService.findById(id).get());
        return "task/info";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateCandidate(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", taskService.findById(id).get());
        return "task/update";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskService.replace(task.getId(), task);
        return "redirect:/tasks";
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/doneTask")
    public String doneTask(Model model) {
        List<Task> doneTasks = taskService.findByDone(true);
        if (doneTasks.isEmpty()) {
            model.addAttribute("message", "There are no done tasks :(");
        }
        model.addAttribute("tasks", doneTasks);
        return "task/done";
    }

    @GetMapping("/activeTask")
    public String activeTask(Model model) {
        List<Task> activeTasks = taskService.findByDone(false);
        if (activeTasks.isEmpty()) {
            model.addAttribute("message", "There are no active tasks :(");
        }
        model.addAttribute("tasks", activeTasks);
        return "task/active";
    }

    @GetMapping("/setDone/{taskId}")
    public String setDone(@PathVariable("taskId") int id) {
        taskService.setDone(id);
        return "redirect:/formTaskInfo/{taskId}";
    }

    @GetMapping("/setActive/{taskId}")
    public String setActive(@PathVariable("taskId") int id) {
        taskService.setActive(id);
        return "redirect:/formTaskInfo/{taskId}";
    }

}
