package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static ru.job4j.todo.util.HttpSetSession.setSession;
import static ru.job4j.todo.util.ZoneSetTime.setTimeZone;

@RequestMapping("/tasks")
@Controller
public class TaskController {

    private final TaskService taskService;
    private final CategoryService categoryService;
    private final PriorityService priorityService;

    public TaskController(TaskService taskService, CategoryService categoryService, PriorityService priorityService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.priorityService = priorityService;
    }

    @GetMapping("")
    public String tasks(Model model, HttpSession httpSession) {
        List<Task> tasks = taskService.findAll();
        if (tasks.isEmpty()) {
            model.addAttribute("message", "Add new task :)");
        }
        User user = (User) httpSession.getAttribute("user");
        tasks.forEach(task -> setTimeZone(user, task));
        model.addAttribute("tasks", tasks);
        model.addAttribute("categories", categoryService.findAll());
        setSession(model, httpSession);
        return "task/tasks";
    }

    @GetMapping("/formAdd")
    public String addTask(Model model, HttpSession httpSession) {
        setSession(model, httpSession);
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "task/add";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, @RequestParam("category.id") List<Integer> categoriesIDs, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        task.setUser(user);
        List<Category> categories = categoryService.findByIds(categoriesIDs);
        if (categoriesIDs.isEmpty() || categories.size() != categoriesIDs.size()) {
            return "redirect:/shared/fail";
        }
        task.setCategories(categories);
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/formInfo/{taskId}")
    public String taskInfo(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        Task task = taskService.findById(id).get();
        setTimeZone(user, task);
        model.addAttribute("task", task);
        setSession(model, httpSession);
        return "task/info";
    }

    @GetMapping("/formUpdate/{taskId}")
    public String formUpdateCandidate(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        model.addAttribute("task", taskService.findById(id).get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        setSession(model, httpSession);
        return "task/update";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task, @RequestParam("category.id") List<Integer> categoriesIDs) {
        List<Category> categories = categoryService.findByIds(categoriesIDs);
        if (!taskService.replace(task.getId(), task) || categoriesIDs.isEmpty() || categories.size() != categoriesIDs.size()) {
            return "redirect:/shared/fail";
        }
        task.setCategories(categories);
        taskService.replace(task.getId(), task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        if (!taskService.delete(id)) {
            return "redirect:/shared/fail";
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
            return "redirect:/shared/fail";
        }
        taskService.setDone(id);
        setSession(model, httpSession);
        return "redirect:/tasks/formInfo/{taskId}";
    }

    @GetMapping("/setActive/{taskId}")
    public String setActive(Model model, @PathVariable("taskId") int id, HttpSession httpSession) {
        if (!taskService.setActive(id)) {
            return "redirect:/shared/fail";
        }
        taskService.setActive(id);
        setSession(model, httpSession);
        return "redirect:/tasks/formInfo/{taskId}";
    }

}
