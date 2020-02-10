package kz.itbc.docshare.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kz.itbc.docshare.database.DAO.CommonDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FolderAccessDAOException;
import kz.itbc.docshare.exception.FolderDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.*;

public class ShareFolderService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        List<User> users = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        FolderDAO fileDAO = new FolderDAO();
        Map<String, String> message = new HashMap<>();
        Folder folder = null;
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        int folder_id = Integer.parseInt(req.getParameter(ID_PARAMETER));
        String stringJson = req.getParameter(USERS_PARAMETER);
        JsonArray json = new JsonParser().parse(stringJson).getAsJsonArray();
        System.out.println("json.size() " + json.size());

        try {
            folder = fileDAO.getFolderById(folder_id);

            if (folder.getUserCreated().getId_User() == user.getId_User() || user.getId_Role() == 2 || user.getId_Role() == 3) {
                if (json.size() == 0) {
                    message.put(ERROR_ATTRIBUTE, "Необходимо выбрать пользователей");
                } else {
                    for (JsonElement jsonElement : json) {
                        try {
                            users.add(userDAO.getUserById(jsonElement.getAsLong()));
                        } catch (UserDAOException e) {
                            SERVICE_LOGGER.error("Ошибка получения пользователя с ID = " + jsonElement.getAsLong());
                            message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
                        }
                    }
                    try {
                        new CommonDAO().addFolderAccess(folder_id, users);
                        message.put(MESSAGE_ATTRIBUTE, "Вы успешно поделились папкой");
                    } catch (FolderAccessDAOException e) {
                        SERVICE_LOGGER.error("Ошибка добавления записи в таблицу доступа к папкам.");
                        message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
                    }
                }
            }
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения папки с ID = " + folder_id);
            message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
        }
        res.setContentType(JSON_CONTENT_TYPE);
        res.setCharacterEncoding(UTF_8_CHARSET);
        PrintWriter writer = res.getWriter();
        String jsonResponse = new Gson().toJson(message);
        writer.write(jsonResponse);
    }
}