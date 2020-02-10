package kz.itbc.docshare.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.*;

public interface Service {

    void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

    void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

    default String getLocale(HttpServletRequest req) {
        String locale = (String) req.getSession().getAttribute(SESSION_LOCALE_ATTRIBUTE);
        if (locale == null) {
            locale = RU_LOCALE_ATTRIBUTE;
        }
        return locale;
    }
}
