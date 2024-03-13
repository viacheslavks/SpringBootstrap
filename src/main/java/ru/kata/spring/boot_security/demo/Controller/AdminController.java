package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping()
    public String showAllUsers(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("currentUser", currentUser);
        return "adminList";




    }

    @GetMapping(value = "/addForm")
    public String addForm(@ModelAttribute("user") User user, Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);
        return "addForm";
    }

    @PostMapping(value = "/addForm")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("selectedRoles") List<Long> selectResult) {
        List<Role> roles = new ArrayList<>();
        for (Long s : selectResult) {
            roles.add(roleService.getRoleById(s));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/editForm")
    public String editForm(@RequestParam("userId") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        List<Role> roles = (List<Role>) roleService.getAllRoles();
        model.addAttribute("allRoles", roles);
        return "update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("selectedRoles") List<Long> selectResult) {
        List<Role> roles = new ArrayList<>();
        for (Long s : selectResult) {
            roles.add(roleService.getRoleById(s));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
