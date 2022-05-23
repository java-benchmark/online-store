package com.gmail.artemkrotenok.web.controller;

import com.gmail.artemkrotenok.repository.model.UserRoleEnum;
import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.model.UpdateUserDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owasp.encoder.Encode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.gmail.artemkrotenok.web.constant.ControllerConstant.FIRST_PAGE_FOR_PAGINATION;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;

    public UserController(
            UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsersPage(
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        Long countUsers = userService.getCountUsers();
        model.addAttribute("countUsers", countUsers);
        model.addAttribute("page", page);
        List<UserDTO> usersDTO = userService.getItemsByPageSorted(page);
        model.addAttribute("users", usersDTO);
        model.addAttribute("userUpdate", new UpdateUserDTO());
        return "users";
    }

    @PostMapping
    public String addUser(
            Model model,
            @Valid @ModelAttribute(name = "user") UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            model.addAttribute("roles", Arrays.asList(UserRoleEnum.values()));
            return "user_add";
        }
        userService.add(userDTO);
        String message = "User " + userDTO.getName() + " was added successfully";
        model.addAttribute("message", message);
        model.addAttribute("redirect", "/users");
        logger.info("addUser: {}", message);
        return "message";
    }

    @PostMapping("/add")
    public String addUser2(
            Model model,
            @Valid @ModelAttribute(name = "user") UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            model.addAttribute("roles", Arrays.asList(UserRoleEnum.values()));
            return "user_add";
        }
        userService.add(userDTO);
        model.addAttribute("message", "User " + userDTO.getName() + " " + Encode.forHtmlContent(userDTO.getSurname()) + " with address " + Encode.forHtmlContent(userDTO.getAddress()) + " was added successfully");
        model.addAttribute("redirect", "/users");
        return "message";
    }

    @PostMapping("/update")
    public String updateUser(
            @ModelAttribute(name = "userUpdate") UpdateUserDTO updateUserDTO,
            Model model
    ) {
        UserDTO userDTO = userService.getUserById(updateUserDTO.getId());
        model.addAttribute("redirect", "/users");
        if (userDTO != null) {
            StringBuilder resultMessage = new StringBuilder();
            resultMessage.append("For user '").append(userDTO.getEmail()).append("': ");
            if (updateUserDTO.getRole() != null) {
                userService.changeUserRole(updateUserDTO);
                resultMessage.append("role change saved");
            }
            if ((updateUserDTO.getChangePassword() != null) && updateUserDTO.getChangePassword()) {
                userService.changeUserPassword(updateUserDTO.getId());
                resultMessage.append("new password send to e-mail");
            }
            model.addAttribute("message", resultMessage.toString());
            return "message";
        }
        model.addAttribute("message", "Error selected user, change not saved");
        return "message";
    }

    @PostMapping("/role")
    public String getChangeUserRolePage(
            @ModelAttribute(name = "userUpdate") UpdateUserDTO updateUserDTO,
            Model model) {
        UserDTO userDTO = userService.getUserById(updateUserDTO.getId());
        model.addAttribute("user", userDTO);
        model.addAttribute("roles", Arrays.asList(UserRoleEnum.values()));
        model.addAttribute("userUpdate", new UpdateUserDTO());
        return "user_role_change";
    }

    @PostMapping("/delete")
    public String deleteUsers(
            @RequestParam(name = "userIds", required = false) Long[] userIds,
            Model model
    ) {
        if (userIds == null) {
            model.addAttribute("message", "User is not selected");
        } else {
            userService.deleteUsersByIds(userIds);
            model.addAttribute("message", "Selected users was deleted successfully");
        }
        model.addAttribute("redirect", "/users");
        return "message";
    }

    @GetMapping("/add")
    public String getAddUserPage(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", Arrays.asList(UserRoleEnum.values()));
        return "user_add";
    }

    @GetMapping("/profile")
    public String getProfileUserPage(
            Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", userDTO);
        return "user_profile";
    }

    @PostMapping("/profile")
    public String updateProfileUserPage(
            Model model,
            @ModelAttribute(name = "user") UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "user_profile";
        }
        userService.update(userDTO);
        model.addAttribute("message", "User profile was update successfully");
        model.addAttribute("redirect", "/home");
        return "message";
    }

}
