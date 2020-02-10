/*
package kz.itbc.docshare.filter;

import kz.itbc.docshare.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.LOGIN_PAGE_URI;
import static kz.itbc.docshare.constants.AppConstant.USER_ATTRIBUTE;

public class AccessFilter extends HttpFilter {
    private final String[] extensions = {".ico", ".gif", ".css", ".js", ".ttf", ".woff", ".woff2", ".svg", ".eot",
            ".jpg", ".png"};

    private boolean isExtensions(HttpServletRequest request) {
        String context = request.getContextPath();
        String path = request.getRequestURI().substring(context.length());
        for (String item : extensions) {
            if (path.endsWith(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        String reqURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        if (isExtensions(req)) {
            chain.doFilter(req, res);
        } else {
            if (reqURI.contains(LOGIN_PAGE_URI) || reqURI.substring(contextPath.length()).equals("/") ||
                    reqURI.substring(contextPath.length()).equals("#")) {
                filterLoginPage(req, res, chain);
            } else {
                filterAllPages(req, res, chain);
            }
        }
    }

    private void filterLoginPage(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        final HttpSession session = req.getSession();
        User tempUser = (User) session.getAttribute(USER_ATTRIBUTE);
        if (tempUser != null) {
            res.sendRedirect(req.getContextPath() + IN_WORK_PAGE_URI);
        } else {
            chain.doFilter(req, res);
        }
    }

    private void filterAllPages(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        final HttpSession session = req.getSession();
        User tempUser = (User) session.getAttribute(USER_ATTRIBUTE);
        if (tempUser == null) {
            res.sendRedirect(req.getContextPath() + LOGIN_PAGE_URI);
        } else {
            chain.doFilter(req, res);
        }
    }
}*/
