package com.gmail.artemkrotenok.web.controller;

import com.gmail.artemkrotenok.service.FeedbackService;
import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.model.FeedbackDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.validation.Valid;

import static com.gmail.artemkrotenok.web.constant.ControllerConstant.FIRST_PAGE_FOR_PAGINATION;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;

    public FeedbackController(
            FeedbackService feedbackService,
            UserService userService
    ) {
        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    @GetMapping
    public String getFeedbackPage(
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        Long countFeedbacks = feedbackService.getCountFeedback();
        model.addAttribute("countFeedbacks", countFeedbacks);
        model.addAttribute("page", page);
        List<FeedbackDTO> feedbacksDTO = feedbackService.getItemsByPage(page);
        model.addAttribute("feedbacks", feedbacksDTO);
        return "feedbacks";
    }

    @GetMapping("/add")
    public String getFeedbackAddPage(Model model) {
        model.addAttribute("feedback", new FeedbackDTO());
        return "feedback_add";
    }

    @PostMapping("/add")
    public String addFeedback(
            Model model,
            @Valid @ModelAttribute(name = "feedback") FeedbackDTO feedbackDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("feedback", feedbackDTO);
            return "feedback_add";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userService.getUserByEmail(authentication.getName());
        feedbackDTO.setUserDTO(userDTO);
        feedbackService.add(feedbackDTO);
        model.addAttribute("message", "New feedback was added successfully");
        model.addAttribute("redirect", "/home");
        return "message";
    }

    @PostMapping("/delete")
    public String deleteFeedback(
            @RequestParam(name = "feedbackId") Long feedbackId,
            Model model) {
        feedbackService.deleteById(feedbackId);
        model.addAttribute("message", "Selected feedback was deleted successfully");
        model.addAttribute("redirect", "/feedbacks");
        return "message";
    }

}
