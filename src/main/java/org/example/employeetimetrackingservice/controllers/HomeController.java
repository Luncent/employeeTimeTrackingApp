package org.example.employeetimetrackingservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/worker")
    public String workerHome(Model model){
        return "workerHome";
    }

    @GetMapping("/admin")
    public String adminHome(Model model){
        return "admin/adminUsers";
    }

    @GetMapping("/manager")
    public String managerHome(Model model){
        return "managerHome";
    }
}
