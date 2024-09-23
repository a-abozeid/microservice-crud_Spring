package com.SlicK.api_gateway.Filter;

import com.SlicK.api_gateway.Utils.CachedBodyHttpServletRequest;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestValidationFilter implements Filter {
    private static final Pattern SCRIPT_INJECTION_PATTERN = Pattern.compile("[<>]");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("RequestValidationFilter initialized");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpRequest);
        String requestBody = new String(cachedRequest.getCachedBody());

        if (SCRIPT_INJECTION_PATTERN.matcher(requestBody).find()) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Invalid characters in request body.");
            return;
        }

        filterChain.doFilter(cachedRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("RequestValidationFilter destroyed");
        Filter.super.destroy();
    }
}
