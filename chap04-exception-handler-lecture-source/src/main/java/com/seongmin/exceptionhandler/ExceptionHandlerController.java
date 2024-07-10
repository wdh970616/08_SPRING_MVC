package com.seongmin.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {

    @GetMapping("controller-null")
    public String nullPointExceptionTest() {

        String str = null;
        System.out.println(str.charAt(0));

        return "/";
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointExceptionHandler(NullPointerException exception) {
        System.out.println("Controller 레벨의 exception 처리");
        return "error/nullPointer";
    }

    @GetMapping("controller-user")
    public String userExceptionTest() throws MemberRegistException {

        boolean check = true;

        if (check) {
            throw new MemberRegistException("당신은 우리와 함께 할 수 없습니다.");
        }

        return "/";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(Model model, MemberRegistException exception) {

        System.out.println("Controller 레벨의 exception 처리");

        model.addAttribute(exception);

        return "error/memberRegist";
    }
}
