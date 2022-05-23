package com.gmail.artemkrotenok.web.controller;

import java.util.List;
import javax.validation.Valid;

import com.gmail.artemkrotenok.service.NewsService;
import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.model.NewsDTO;
import com.gmail.artemkrotenok.service.model.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.gmail.artemkrotenok.web.constant.ControllerConstant.FIRST_PAGE_FOR_PAGINATION;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;

    public NewsController(
            NewsService newsService,
            UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping
    public String getNewsPage(
            @RequestParam(name = "page", required = false) Integer page,
            Model model
    ) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        Long countNews = newsService.getCountNews();
        model.addAttribute("countNews", countNews);
        model.addAttribute("page", page);
        List<NewsDTO> newsDTOList = newsService.getItemsByPageSorted(page);
        model.addAttribute("newsList", newsDTOList);
        return "news";
    }

    @GetMapping("/{id}")
    public String getNewsPage(
            @PathVariable Long id,
            Model model) {
        NewsDTO newsDTO = newsService.findById(id);
        model.addAttribute("news", newsDTO);
        return "news_details";
    }

    @GetMapping("/add")
    public String getAddNewsPage(Model model) {
        model.addAttribute("news", new NewsDTO());
        return "news_add";
    }

    @PostMapping("/update")
    public String updateNews(
            Model model,
            @ModelAttribute(name = "news") NewsDTO newsDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("news", newsDTO);
            return "news_update";
        }
        newsService.update(newsDTO);
        model.addAttribute("message", "News was update successfully");
        model.addAttribute("redirect", "/news");
        return "message";
    }

    @GetMapping("/update")
    public String getUpdateNews(
            @RequestParam(name = "newsId") Long newsId,
            Model model) {
        NewsDTO newsDTO = newsService.findById(newsId);
        if (newsDTO == null) {
            model.addAttribute("message", "Article not found for id='" + newsId + "'");
            model.addAttribute("redirect", "/news");
            return "message";
        }
        model.addAttribute("news", newsDTO);
        return "news_update";
    }

    @PostMapping("/delete")
    public String deleteNews(
            @RequestParam(name = "newsId") Long newsId,
            Model model) {
        newsService.deleteById(newsId);
        model.addAttribute("message", "News was deleted successfully");
        model.addAttribute("redirect", "/news");
        return "message";
    }

    @PostMapping
    public String addArticle(
            Model model,
            @Valid @ModelAttribute(name = "news") NewsDTO newsDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("news", newsDTO);
            return "news_add";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userService.getUserByEmail(authentication.getName());
        newsDTO.setUserDTO(userDTO);
        newsService.add(newsDTO);
        model.addAttribute("message", "New article was added successfully");
        model.addAttribute("redirect", "/news");
        return "message";
    }

}
