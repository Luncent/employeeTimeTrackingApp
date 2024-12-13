package org.example.employeetimetrackingservice.controllers;

import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app/worker")
public class WorkerPagesControlles {
    @Autowired
    private UserService userService;

    @GetMapping("/workerSchedule")
    public String workerSchedule(@CookieValue(value = "uid") Long userId,
                             Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "worker/workerSchedule";
    }

    @GetMapping("/workerScheduleChoose")
    public String workerScheduleChoose(@CookieValue(value = "uid") Long userId,
                                Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "worker/workerScheduleChoose";
    }

    @GetMapping("/workerVacations")
    public String workerVacations(@CookieValue(value = "uid") Long userId,
                             Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "worker/workerVacations";
    }

    @GetMapping("/workerWork")
    public String workerWork(@CookieValue(value = "uid") Long userId,
                                  Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "worker/workerWork";
    }
}
