package org.example.employeetimetrackingservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("app/admin")
public class AdminPagesController {
    @GetMapping("/adminUsers")
    public String adminUsers(Model model){
        return "admin/adminUsers";
    }

    @GetMapping("/adminHolidays")
    public String adminHolidays(Model model){
        return "admin/adminHolidays";
    }

    @GetMapping("/adminRoles")
    public String adminRoles(Model model){
        return "admin/adminRoles";
    }

    @GetMapping("/adminDepartments")
    public String workerHome(Model model){
        return "admin/adminDepartments";
    }
}
