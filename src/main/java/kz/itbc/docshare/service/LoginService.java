package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.UserDAOException;
import kz.itbc.docshare.security.Security;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static kz.itbc.docshare.constants.AppConstant.*;

public class LoginService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PAGE_JSP);
        requestDispatcher.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        final HttpSession session = req.getSession();
        UserDAO userDAO = new UserDAO();
        String login = req.getParameter(LOGIN_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        try {
            User user = userDAO.getUserByLogin(login);
            if(Security.decrypt(password, user.getPassword())){
                if(user.getBlockedDate() == null || !(user.getBlockedDate().getTime() > System.currentTimeMillis())) {
                    session.setAttribute(USER_ATTRIBUTE, user);
                    res.sendRedirect(req.getContextPath() + MAIN_PAGE_URI);
                }else {
                    req.setAttribute(ERROR_ATTRIBUTE, USER_BLOCKED_ERROR);
                    req.setAttribute(BLOCKED_DATE_ATTRIBUTE, user.getBlockedDate());
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PAGE_URI);
                    requestDispatcher.forward(req, res);
                }
            } else {
                req.setAttribute(ERROR_ATTRIBUTE, INCORRECT_PASSWORD_ERROR);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PAGE_URI);
                requestDispatcher.forward(req, res);
            }
        } catch (UserDAOException e){
            req.setAttribute(ERROR_ATTRIBUTE, INCORRECT_LOGIN_ERROR);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOGIN_PAGE_URI);
            requestDispatcher.forward(req, res);
            SERVICE_LOGGER.error("Ошибка получения пользователя по логину: " + login);
        }
    }
}
