package com.seongmin.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
// default 메소드 이전에는 모두 오버라이딩 해야하는 책임을 가지기 때문에 HandlerInterceptorAdaptor를 이용해 부담을 줄여 사용했었다.
// default 메소드가 인터페이스에서 사용가능하게된 1.8 이후부터는 인터페이스만 구현하여 필요한 메소드만 오버라이딩해서 사용할 수 있다.
public class StopWatchInterceptor implements HandlerInterceptor {

    private final MenuService menuService;

//  @Autowired -> 생성자가 한개일땐 자동으로 주입해줌
    public StopWatchInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }

    // 전처리 메소드
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("preHandler 호출함...");

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return false;
    }

    // 후처리 메소드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("postHandler 호출함...");

        long startTime = (long) request.getAttribute("startTime");
        request.removeAttribute("startTime");

        long endTime = System.currentTimeMillis();

        modelAndView.addObject("interval", endTime - startTime);
    }

    // 마지막에 호출하는 메소드
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 호출함...");
        menuService.method();
    }
}
