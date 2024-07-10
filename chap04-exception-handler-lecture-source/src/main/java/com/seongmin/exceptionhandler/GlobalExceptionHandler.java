package com.seongmin.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 전역 예외 처리를 담당함.
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String nullPointExceptionHandler(NullPointerException exception) {
        System.out.println("Global 레벨의 exception 처리");
        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(Model model, MemberRegistException exception) {

        System.out.println("Global 레벨의 exception 처리");

        model.addAttribute(exception);

        return "error/memberRegist";
    }

    // 최상위 타입인 Exception을 이용하면 구체적으로 작성하지 않은 타입의 에러가 발생해도 처리가 가능하므로 default 처리용도로 사용할 수 있다.
    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandler(Exception exception) {
        return "error/default";
    }
}
