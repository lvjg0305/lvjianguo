package deepthinking.aspect;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * URL请求响应时间统计拦截器
 * @Author： bingo
 * @Date： 2018/7/31
 */
public class BootInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(BootInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("ReqTime",System.nanoTime());
        request.setAttribute("url",request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long interval = System.nanoTime() - (Long) request.getAttribute("ReqTime");
        String log = "访问" + (String) request.getAttribute("url") + "消耗" + interval / 1000000 + "毫秒";
        System.out.println(log);
        logger.info(log);
    }
}
