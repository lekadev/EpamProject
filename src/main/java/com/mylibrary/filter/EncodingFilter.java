package com.mylibrary.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter  implements Filter {

    private final String defaultEncoding = "UTF-8";

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String currentEncoding = request.getCharacterEncoding();
        if(currentEncoding != null && !currentEncoding.equals(defaultEncoding)) {
            request.setCharacterEncoding(defaultEncoding);
            response.setCharacterEncoding(defaultEncoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
