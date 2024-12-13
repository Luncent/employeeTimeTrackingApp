package org.example.employeetimetrackingservice.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestParam String userName,
                                        @RequestParam String password,
                                        HttpServletResponse response) throws UnsupportedEncodingException {

        if (userName.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
            Cookie authCookie = new Cookie("role", "admin");
            authCookie.setMaxAge(60 * 60);
            authCookie.setPath("/");
            response.addCookie(authCookie);

            return ResponseEntity.status(200)
                    .header("Location", "app/admin/adminUsers")
                    .build();
        }

        Optional<User> userOptional = userService.auth(userName,password);

        if(!userOptional.isPresent()){
            return ResponseEntity.status(401).body("Неверное имя пользователя или пароль");
        }

        User user = userOptional.get();
        if (user.getRole().getTitle().equalsIgnoreCase("Начальник")) {
            Cookie authCookie = new Cookie("role", "manager");
            authCookie.setMaxAge(60 * 60); //час
            authCookie.setPath("/");       // Кука доступна для всех путей на сайте
            response.addCookie(authCookie);

            Cookie uidCookie = new Cookie("uid", user.getId().toString());
            uidCookie.setMaxAge(60 * 60);
            uidCookie.setPath("/");
            response.addCookie(uidCookie);

            Cookie departmentIdCookie = new Cookie("departmentId", user.getDepartment().getId().toString());
            departmentIdCookie.setMaxAge(60 * 60);
            departmentIdCookie.setPath("/");
            response.addCookie(departmentIdCookie);

            return ResponseEntity.status(200)
                    .header("Location", "/app/manager/managerStatistics")  // редиректа
                    .build();
        }
        else {
            Cookie authCookie = new Cookie("role", "worker");
            authCookie.setMaxAge(60 * 60);
            authCookie.setPath("/");
            response.addCookie(authCookie);

            Cookie uidCookie = new Cookie("uid", user.getId().toString());
            uidCookie.setMaxAge(60 * 60);
            uidCookie.setPath("/");
            response.addCookie(uidCookie);

            Cookie unameCookie = new Cookie("username",  URLEncoder.encode(user.getName(), StandardCharsets.UTF_8.toString()));
            unameCookie.setMaxAge(60 * 60);
            unameCookie.setPath("/");
            response.addCookie(unameCookie);

            Cookie departmentIdCookie = new Cookie("departmentId", user.getDepartment().getId().toString());
            departmentIdCookie.setMaxAge(60 * 60);
            departmentIdCookie.setPath("/");
            response.addCookie(departmentIdCookie);

            return ResponseEntity.status(200)
                    .header("Location", "/app/worker/workerSchedule")  // Указываем новый URL для редиректа
                    .build();
        }
    }
}
