package kz.itbc.docshare.service;

import com.google.gson.Gson;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;

public class GetUsersAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect(req.getContextPath() + MAIN_PAGE_URI);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<User> users = null;
        keyword = keyword.concat("%");
        try {
            users = new UserDAO().getUsersByKeyword(keyword);
        } catch (UserDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения пользователей", e);
        }
        res.setContentType(JSON_CONTENT_TYPE);
        res.setCharacterEncoding(UTF_8_CHARSET);
        PrintWriter writer = res.getWriter();
        String jsonResponse = new Gson().toJson(users);
        writer.write(jsonResponse);
    }
}
