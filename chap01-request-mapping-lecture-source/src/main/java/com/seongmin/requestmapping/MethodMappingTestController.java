package com.seongmin.requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;

/*
 * DispatcherServlet은 웹 요청을 받는 즉시 @Controller가 달린 컨트롤러 클래스에 처리를 위임한다.
 * 그 과정을 컨트롤러 클래스의 핸들러 메소드에 선언된 다양한 @RequestMapping 설정내용에 따른다.
 */
@Controller
public class MethodMappingTestController {

    // 1. 메소드 방식 미지정
    @RequestMapping("/menu/regist")
    public String registMenu(Model model) {

        // Model 객체에 addAttribute 메소드를 이용해 key, value를 추가해 view에서 사용할 수 있다.
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출함...");

        // 반화하고자 하는 view의 경로를 포함한 이름을 작성한다.
        // resources/templates 하위부터의 경로를 작성한다.
        return "page/mappingResult";
    }

    // 2. 메소드 방식 지정
    // 요청URL을 value속성에 요청 method를 method속성에 설정한다.
    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)
    public String modifyMenu(Model model) {

        model.addAttribute("message", "GET 방식의 메뉴 수정용 핸들러 메소드 호출함...");

        return "page/mappingResult";
    }

    @RequestMapping(value = "/menu/modify", method = RequestMethod.POST)
    public String modifyMenuAll(Model model) {

        model.addAttribute("message", "POST 방식의 메뉴 수정용 핸들러 메소드 호출함...");

        return "page/mappingResult";
    }


    @GetMapping("/menu/delete")
//  = @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)
    public String getDeleteMenu(Model model) {

        model.addAttribute("message", "GET 방식의 삭제용 핸들러 메소드 호출함...");

        return "page/mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model) {

        model.addAttribute("message", "POST 방식의 삭제용 핸들러 메소드 호출함...");

        return "page/mappingResult";
    }
}
