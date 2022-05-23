package com.gmail.artemkrotenok.web.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class FormattedTextErrorsUtil {

    public static Object getTextErrors(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        List<String> errors = new ArrayList<>();
        for (ObjectError error : allErrors) {
            errors.add(error.getCode() + " - " + error.getDefaultMessage());
        }
        return errors.toString();
    }

}
