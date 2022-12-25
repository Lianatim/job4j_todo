package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static ru.job4j.todo.util.HttpSetSession.setSession;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String tasks(Model model, HttpSession httpSession) {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            model.addAttribute("message", "Add new task :)");
        }
        model.addAttribute("tasks", tasks);
        setSession(model, httpSession);
        return "task/tasks";
    }

    @GetMapping("/formAddTask")
    public String addTask(Model model, HttpSession httpSession) {
        setSession(model, httpSession);
        return "task/add";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/formTaskInfo/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        model.addAttribute("task", taskService.findById(id).get());
        setSession(model, httpSession);
        return "task/info";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateCandidate(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        model.addAttribute("task", taskService.findById(id).get());
        setSession(model, httpSession);
        return "task/update";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskService.replace(task.getId(), task);
        return "redirect:/tasks";
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        taskService.delete(id);
        setSession(model, httpSession);
        return "redirect:/tasks";
    }

    @GetMapping("/doneTask")
    public String doneTask(Model model, HttpSession httpSession) {
        List<Task> doneTasks = taskService.findByDone(true);
        if (doneTasks.isEmpty()) {
            model.addAttribute("message", "There are no done tasks :(");
        }
        model.addAttribute("tasks", doneTasks);
        setSession(model, httpSession);
        return "task/done";
    }

    @GetMapping("/activeTask")
    public String activeTask(Model model, HttpSession httpSession) {
        List<Task> activeTasks = taskService.findByDone(false);
        if (activeTasks.isEmpty()) {
            model.addAttribute("message", "There are no active tasks :(");
        }
        model.addAttribute("tasks", activeTasks);
        setSession(model, httpSession);
        return "task/active";
    }

    @GetMapping("/setDone/{taskId}")
    public String setDone(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        taskService.setDone(id);
        setSession(model, httpSession);
        return "redirect:/formTaskInfo/{taskId}";
    }

    @GetMapping("/setActive/{taskId}")
    public String setActive(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        taskService.setActive(id);
        setSession(model, httpSession);
        return "redirect:/formTaskInfo/{taskId}";
    }

}
