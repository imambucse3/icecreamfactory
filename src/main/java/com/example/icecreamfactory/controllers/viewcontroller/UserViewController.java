package com.example.icecreamfactory.controllers.viewcontroller;

import com.example.icecreamfactory.dao.UserDto;
import com.example.icecreamfactory.entity.Role;
import com.example.icecreamfactory.entity.User;
import com.example.icecreamfactory.repository.RoleRepository;
import com.example.icecreamfactory.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserViewController {
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "The User with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/users";
    }

    @GetMapping("/edit/user/{id}")
    public String editregistrationForm(@PathVariable("id") Long id, Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        UserDto userDto = userService.findByUserId(id);
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("user", userDto);
        model.addAttribute("roles", roles);
        return "editUser";
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam(value = "userId") Long id, Model model){
        // create model object to store form data
       userService.deleteUser(id);
       return "userList";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "register";
    }

    @PostMapping ("/update/user/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDto userDto,
                             BindingResult result, Model model) {
        UserDto existingUser = userService.findByUserId(id);
        Long userId = existingUser.getId();
        if (existingUser != null) {
            UserDto userDto1 = userService.updateUser(userDto);
            model.addAttribute("user", userDto1);
            return "redirect:/users?success";
        }
        model.addAttribute("user", existingUser);
        return "redirect:/users?danger";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }
}
