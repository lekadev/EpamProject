package com.mylibrary.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter  implements Filter {

    private static final String DEFAULT_ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig config) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String currentEncoding = request.getCharacterEncoding();
        if(currentEncoding != null && !currentEncoding.equals(DEFAULT_ENCODING)) {
            request.setCharacterEncoding(DEFAULT_ENCODING);
            response.setCharacterEncoding(DEFAULT_ENCODING);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
