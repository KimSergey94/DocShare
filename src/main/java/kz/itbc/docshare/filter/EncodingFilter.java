package kz.itbc.docshare.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.ENCODING;

public class EncodingFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws
            IOException, ServletException {
        req.setCharacterEncoding(ENCODING);
        chain.doFilter(req, res);
    }
}
