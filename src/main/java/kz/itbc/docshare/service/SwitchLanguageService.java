package kz.itbc.docshare.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.*;

public class SwitchLanguageService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        doGet(req, res);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws  IOException {
        final HttpSession session = req.getSession();
        final String referer = req.getHeader( "referer" );
        final String sessionLocale = (String) session.getAttribute(SESSION_LOCALE_ATTRIBUTE);
        final String switchingLocale = req.getParameter("sl");
        if(switchingLocale.equals(KZ_LOCALE_ATTRIBUTE) || switchingLocale.equals(RU_LOCALE_ATTRIBUTE)){
            if (sessionLocale == null || !sessionLocale.equals(switchingLocale)){
                session.setAttribute(SESSION_LOCALE_ATTRIBUTE, switchingLocale );
                res.sendRedirect(referer);
            } else {
                res.sendRedirect(referer);
            }
        } else {
            res.sendRedirect(referer);
        }
    }
}
