package com.gmail.artemkrotenok.web.controller;

import java.util.List;
import javax.validation.Valid;

import com.gmail.artemkrotenok.service.NewsService;
import com.gmail.artemkrotenok.service.model.NewsDTO;
import com.gmail.artemkrotenok.web.util.FormattedTextErrorsUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.artemkrotenok.web.constant.ControllerConstant.FIRST_PAGE_FOR_PAGINATION;

@RestController
@RequestMapping("/api/articles")

public class APINewsController {

    private final NewsService newsService;

    public APINewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public List<NewsDTO> getNewsByPage(
            @RequestParam(name = "page", required = false) Integer page) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        return newsService.getItemsByPageSorted(page);
    }

    @GetMapping(value = "/{id}")
    public NewsDTO getNewsById(@PathVariable(name = "id") Long id) {
        return newsService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addNewNews(@RequestBody @Valid NewsDTO newsDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(FormattedTextErrorsUtil.getTextErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newsService.add(newsDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteItem(@PathVariable(name = "id") Long id) {
        return newsService.deleteById(id);
    }

}
