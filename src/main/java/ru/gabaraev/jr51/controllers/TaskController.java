package ru.gabaraev.jr51.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gabaraev.jr51.models.Task;
import ru.gabaraev.jr51.service.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final Map<String, String> statusMap = new HashMap<>();

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        statusMap.put("0", "IN_PROGRESS");
        statusMap.put("1", "DONE");
        statusMap.put("2", "PAUSED");
    }

    @GetMapping()
    public String listTasks(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Task> pageTasks = taskService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("tasks", pageTasks);

        int totalPages = pageTasks.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "tasks/list_tasks";
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("statusMap", statusMap);
        return "tasks/form";
    }

    @PostMapping("/edit")
    public String getEditForm(@RequestParam(name = "taskId") Long taskId, Model model) {
        model.addAttribute("task", taskService.getTaskById(taskId));
        model.addAttribute("statusMap", statusMap);
        return "tasks/edit_form";
    }

    @PostMapping("/create")
    @ResponseBody
    public void createTask(@RequestParam(name = "taskDescription") String taskDescription,
                           @RequestParam(name = "taskStatus") String taskStatus) {

        taskService.save(taskDescription, taskStatus);
    }

    @PostMapping("/update")
    @ResponseBody
    public void updateTask(@RequestParam(name = "taskId") Long taskId,
                           @RequestParam(name = "taskDescription") String taskDescription,
                           @RequestParam(name = "taskStatus") String taskStatus) {
        taskService.update(taskId, taskDescription, taskStatus);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void deleteTask(@RequestParam(name = "taskId") Long taskId) {
        taskService.delete(taskId);
    }
}
