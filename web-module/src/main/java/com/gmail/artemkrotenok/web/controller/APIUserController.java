package com.gmail.artemkrotenok.web.controller;

import java.lang.invoke.MethodHandles;
import javax.validation.Valid;

import com.gmail.artemkrotenok.service.UserService;
import com.gmail.artemkrotenok.service.model.UserDTO;
import com.gmail.artemkrotenok.web.util.FormattedTextErrorsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")

public class APIUserController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;

    public APIUserController(
            UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> addNewUser(@RequestBody @Valid UserDTO userDTO,
            BindingResult bindingResult) {
        logger.info("addNewUser: userDTO={}", userDTO);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(FormattedTextErrorsUtil.getTextErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        logger.info("addNewUser: name={}", userDTO.getName());
        logger.warn("addNewUser: email={}", Encode.forHtml(userDTO.getEmail()));
        return new ResponseEntity<>(userService.add(userDTO), HttpStatus.CREATED);
    }

}
