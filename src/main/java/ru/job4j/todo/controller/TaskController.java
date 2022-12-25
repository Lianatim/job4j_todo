package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static ru.job4j.todo.util.HttpSetSession.setSession;

@RequestMapping("/tasks")
@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public String tasks(Model model, HttpSession httpSession) {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            model.addAttribute("message", "Add new task :)");
        }
        model.addAttribute("tasks", tasks);
        setSession(model, httpSession);
        return "task/tasks";
    }

    @GetMapping("/formAdd")
    public String addTask(Model model, HttpSession httpSession) {
        setSession(model, httpSession);
        return "task/add";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/formInfo/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        model.addAttribute("task", taskService.findById(id).get());
        setSession(model, httpSession);
        return "task/info";
    }

    @GetMapping("/formUpdate/{taskId}")
    public String formUpdateCandidate(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        model.addAttribute("task", taskService.findById(id).get());
        setSession(model, httpSession);
        return "task/update";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task) {
        if (!taskService.replace(task.getId(), task)) {
            return "redirect:/tasks/fail";
        }
        taskService.replace(task.getId(), task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        if (!taskService.delete(id)) {
            return "redirect:/tasks/fail";
        }
        taskService.delete(id);
        setSession(model, httpSession);
        return "redirect:/tasks";
    }

    @GetMapping("/done")
    public String doneTask(Model model, HttpSession httpSession) {
        List<Task> doneTasks = taskService.findByDone(true);
        if (doneTasks.isEmpty()) {
            model.addAttribute("message", "There are no done tasks :(");
        }
        model.addAttribute("tasks", doneTasks);
        setSession(model, httpSession);
        return "task/done";
    }

    @GetMapping("/active")
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
        if (!taskService.setDone(id)) {
            return "redirect:/tasks/fail";
        }
        taskService.setDone(id);
        setSession(model, httpSession);
        return "redirect:/tasks/formInfo/{taskId}";
    }

    @GetMapping("/setActive/{taskId}")
    public String setActive(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        if (!taskService.setActive(id)) {
            return "redirect:/tasks/fail";
        }
        taskService.setActive(id);
        setSession(model, httpSession);
        return "redirect:/tasks/formInfo/{taskId}";
    }

}
