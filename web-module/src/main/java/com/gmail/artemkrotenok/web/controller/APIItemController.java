package com.gmail.artemkrotenok.web.controller;

import java.util.List;
import javax.validation.Valid;

import com.gmail.artemkrotenok.service.ItemService;
import com.gmail.artemkrotenok.service.model.ItemDTO;
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
@RequestMapping("/api/items")

public class APIItemController {

    private final ItemService itemService;

    public APIItemController(
            ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemDTO> getItemsByPage(
            @RequestParam(name = "page", required = false) Integer page) {
        if (page == null) {
            page = FIRST_PAGE_FOR_PAGINATION;
        }
        return itemService.getItemsByPageSorted(page);
    }

    @GetMapping(value = "/{id}")
    public ItemDTO getItemById(@PathVariable(name = "id") Long id) {
        return itemService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody @Valid ItemDTO itemDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(FormattedTextErrorsUtil.getTextErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(itemService.add(itemDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteItem(@PathVariable(name = "id") Long id) {
        return itemService.deleteById(id);
    }

}
