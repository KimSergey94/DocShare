package kz.itbc.docshare.service;

import com.google.gson.*;
import kz.itbc.docshare.database.DAO.CommonDAO;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileAccessDAOException;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import static kz.itbc.docshare.constants.AppConstant.UTF_8_CHARSET;

public class ShareFilesService implements Service {
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
        Map<String, String> message = new HashMap<>();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        List<File> files = new ArrayList<>();
        FileDAO fileDAO = new FileDAO();
        String stringJsonOpenedFiles = req.getParameter(OPENED_FILES_PARAMETER);
        JsonArray jsonFiles = new JsonParser().parse(stringJsonOpenedFiles).getAsJsonArray();
        String stringJsonUsers = req.getParameter(USERS_PARAMETER);
        JsonArray jsonUsers = new JsonParser().parse(stringJsonUsers).getAsJsonArray();

        try {
            for (JsonElement jsonElement : jsonFiles) {
                try {
                    files.add(fileDAO.getFileById(jsonElement.getAsLong()));
                } catch (FileDAOException | JsonSyntaxException e) {
                    SERVICE_LOGGER.error("Ошибка получения файла с ID = " + jsonElement.getAsLong());
                }
            }

            if (jsonUsers.size() == 0) {
                message.put(ERROR_ATTRIBUTE, "Необходимо выбрать исполнителей");
            } else {
                for (JsonElement jsonElement : jsonUsers) {
                    try {
                        users.add(userDAO.getUserById(jsonElement.getAsLong()));

                        for(File file : files) {
                            if (file.getUserCreated().getId_User() == user.getId_User() || user.getId_Role() == 2 || user.getId_Role() == 3) {
                                try {
                                    new CommonDAO().addFileAccess(file.getId_File(), users);
                                } catch (FileAccessDAOException e) {
                                    SERVICE_LOGGER.error("Ошибка добавления записи в таблицу доступа к файлам.");
                                    message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
                                }
                            }
                        }
                        message.put(MESSAGE_ATTRIBUTE, "Вы успешно поделились файлом");
                    } catch (UserDAOException e) {
                        SERVICE_LOGGER.error("Ошибка получения пользователя с ID = " + jsonElement.getAsLong());
                        message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
                    }
                }
            }
        } catch (Exception e) {
            SERVICE_LOGGER.error("Возникла ошибка",e);
            message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
        }
        res.setContentType(JSON_CONTENT_TYPE);
        res.setCharacterEncoding(UTF_8_CHARSET);
        PrintWriter writer = res.getWriter();
        String jsonResponse = new Gson().toJson(message);
        writer.write(jsonResponse);
    }
}