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
@RequestMapping("/app/manager")
public class ManagerPagesController {
    @Autowired
    private UserService userService;

    @GetMapping("/managerVacations")
    public String adminUsers(@CookieValue(value = "uid", defaultValue = "guest") Long userId,
                             Model model){
        User user = userService.findById(userId);
        model.addAttribute("user",user);
        return "manager/managerVacations";
    }

    @GetMapping("/managerDepartmentSchedule")
    public String adminHolidays(@CookieValue(value = "uid", defaultValue = "guest") Long userId,
                                Model model){
        User user = userService.findById(userId);
        model.addAttribute("user",user);
        return "manager/managerDepartmentSchedule";
    }

    @GetMapping("/managerStatistics")
    public String adminRoles(@CookieValue(value = "uid", defaultValue = "guest") Long userId,
                             Model model){
        User user = userService.findById(userId);
        model.addAttribute("user",user);
        return "manager/managerStatistics";
    }
}
