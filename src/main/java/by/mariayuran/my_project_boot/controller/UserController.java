package by.mariayuran.my_project_boot.controller;

import by.mariayuran.my_project_boot.dto.UserDto;
import by.mariayuran.my_project_boot.model.User;
import by.mariayuran.my_project_boot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        List<UserDto> users = userService.getAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "user";
    }

    @PostMapping("/new")
    public String saveUser(Model model, UserDto userDto) {
        if (userService.save(userDto)) {
            return "redirect:/users";
        } else {
            model.addAttribute(userDto);
            return "user";
        }
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You are not authorized");
        }
        User user = userService.findByName(principal.getName());
        UserDto dto = UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", dto);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDto dto, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), dto.getName())) {
            throw new RuntimeException("You are not authorize");
        }
        if(dto.getPassword() != null
        && !dto.getPassword().isEmpty()
        && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())){
            model.addAttribute("user",dto);
            return "profile";
        }
        userService.updateProfile(dto);
        return "redirect:/users/profile";
    }

}
