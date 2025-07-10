package unmsm.BibliotecaCentralPedroZulen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unmsm.BibliotecaCentralPedroZulen.model.User;
import unmsm.BibliotecaCentralPedroZulen.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsersWithUserRole() {
        return userService.getUsersByRole("ROLE_USER");
    }
}