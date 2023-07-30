package com.example.student.controller;

import com.example.student.entity.Employee;
import com.example.student.entity.Task;
import com.example.student.service.EmployeeService;
import com.example.student.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TaskController {

    private final EmployeeService employeeService;

    private final TaskService taskService;

    public TaskController(EmployeeService employeeService, TaskService taskService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
    }

    @GetMapping("/employees/tasks/{id}")
    public String listTasks(Model model, @PathVariable Integer id) {
        model.addAttribute("tasks", taskService.getTasksById(id));
        model.addAttribute("employeeId", id);
        return "all_tasks";
    }

    @GetMapping("/employees/deleteTask/{id}")
    public String deleteServiceById(HttpServletRequest request, @PathVariable int id) {
        String referer = request.getHeader("Referer");
        taskService.deleteTaskById(id);
        return "redirect:" + referer;
    }

    @PostMapping("/employees/saveTask/{id}")
    public String saveTask(@ModelAttribute("task") Task task) {
        taskService.saveTask(task);
        return "redirect:/employees/tasks/{id}";
    }

    @GetMapping("/employees/newTask/{id}")
    public String createTask(Model model, @PathVariable int id) {
        Task task = new Task();
        Employee employee = employeeService.getEmployeeById(id);
        task.setEmployeeId(employee);
        model.addAttribute("task", task);
        return "create_task";
    }
}
