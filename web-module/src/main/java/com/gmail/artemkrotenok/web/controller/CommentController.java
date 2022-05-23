package com.gmail.artemkrotenok.web.controller;

import com.gmail.artemkrotenok.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(
            CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/delete")
    public String deleteFeedback(
            @RequestParam(name = "commentId") Long commentId,
            Model model) {
        commentService.deleteById(commentId);
        model.addAttribute("message", "Selected comment was deleted successfully");
        model.addAttribute("redirect", "/news");
        return "message";
    }

}
