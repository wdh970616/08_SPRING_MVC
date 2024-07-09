package com.seongmin.handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
@RequestMapping("/first/*")
public class FirstController {

    /*
     * 컨트롤러 핸들러 메소드의 반환값을 void로 설정하면 요청 주소가 view의 이름이 된다.
     * => /first/regist 요청이 들어오면 /first/regist 뷰를 응답한다.
     */

    // 페이지 이동용 핸들러 메소드
    @GetMapping("regist")
    public void regist() {
    }

    /*
     * WebRequest로 요청 파라미처 전달 받기
     * 파라미터 선언부에 WebRequest 타입을 선언하면 해당 메소드 호출시 인자로 값을 전달해준다.
     * HttpServletRequest, HttpServletResponse / servletRequest, ServletResponse 가능하다.
     */
    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request) {

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        System.out.println("name = " + name);
        System.out.println("price = " + price);
        System.out.println("categoryCode = " + categoryCode);

        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 " + price + "원으로 등록하셨습니다!";
        System.out.println(message);

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    /*
     * @RequestParam으로 요청 파리미터 전달받기
     * 요청 파라미터를 매핑해 호출시 값을 넣어주는 어노테이션으로 매개변수 앞에 작성한다.
     * form의 name 속성값과 매개변수의 이름이 다른 경우 @RequestParam("이름")으로 설정하면 된다.
     * 생략이 가능하지만 가독성을 위해서 명시해주는것이 좋다.
     * required = false 속성 : 값이 들어오지 않아도 400 에러를 발생시키지 않는다. (기본값은 true)
     * defaultValue 속성 : 기본값을 설정
     */

    @GetMapping("modify")
    public void modify() {}

    @PostMapping("modify")
    public String modifyMenuPrice(Model model, @RequestParam(required = false) String modifyName, @RequestParam(defaultValue = "0") int modifyPrice) {

        String message = modifyName + " 메뉴의 가격을 " + modifyPrice + "원으로 변경하였습니다.";
        System.out.println("message = " + message);

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    /*
     * 파라미터가 여러개인 경우 Map으로 한 번에 처리할 수 있다.
     * 이 때, Map의 키는 form의 name 속성값이 된다.
     */

    @PostMapping("modifyAll")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameters) {

        String modifyName = parameters.get("modifyName2");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice2"));

        String message = modifyName + " 메뉴의 가격을 " + modifyPrice + "원으로 변경하였습니다.";
        System.out.println("message = " + message);

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    // /first/search
    @GetMapping("search")
    public void search() {}

    /*
     * @ModelAttribute
     * - DTO 같은 모델을 커맨드 객체로 전달받는다.
     * @ModelAttribute의 경우 커맨드객체를 생성하여 매개변수로 전달해준뒤 인스턴스를 model에 담는다.
     * 경우에 따라 폼에서 입력한 값을 다음 화면으로 바로 전달해야 할 경우가 발생하는데,이때 유용하게 사용된다.
     * @ModelAttribute("모델에담을key값")을 지정할 수 있으며, 지정하지 않으면 타입의 앞글자를 소문자로 한 네이밍 규칙을 따른다.
     * 해당 어노테이션은 생략이 가능하지만 명시적으로 작성하는 것이 좋다.
     * 이 때 주의할 점은 DTO와 입력받을 폼을 잘 맞춰줘야한다.
     */

    @PostMapping("search")
    public String searchMenu(@ModelAttribute MenuDTO menu) {

        System.out.println("menu = " + menu);

        return "first/searchResult";
    }

    @GetMapping("login")
    public void login() {}

    /*
     * session 이용하기
     * HttpSession을 매개변수로 선언하면 핸들러 메소드 호출 시 세션 객체를 넣어서 호출한다.
     */
    @PostMapping("login1")
    public String sessionTest1(HttpSession session, @RequestParam String id) {

        System.out.println("session = " + session);
        System.out.println("id = " + id);

        session.setAttribute("id", id);

        return "first/loginResult";
    }
}
