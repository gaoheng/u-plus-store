package me.gaoheng.uplusstore.web.datatables;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class DTRequestArgumentsResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(DTRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        DTRequest dtRequest = new DTRequest();

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        int draw = Integer.parseInt(request.getParameter("draw"));
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        dtRequest.setDraw(draw);
        dtRequest.setStart(start);
        dtRequest.setLength(length);

        return dtRequest;
    }
}
