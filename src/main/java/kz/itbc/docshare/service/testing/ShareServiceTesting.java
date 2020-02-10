package kz.itbc.docshare.service.testing;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.ERROR_ATTRIBUTE;

public class ShareServiceTesting {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DeleteFileServiceTesting.class);

    public static void main(String[] args) {
        String fileID = "10004";
        List<User> users = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        FileDAO fileDAO = new FileDAO();
        Map<String, String> message = new HashMap<>();
        File file = null;
        User user = null;
        try {
            user = userDAO.getUserById(3);
        } catch (UserDAOException e) {
            System.out.println("Ошибка получения 'админа'");
        }
        long file_id = 0;
        try{
            file_id = Long.parseLong(fileID);
        } catch (NumberFormatException e) {
            SERVICE_LOGGER.error("Неправильный формат передаваемого числа", e);
        }

        String jsonString = "[{\"ID_User\":\"2\",\"ID_File\":\"10004\"}, {\"ID_User\":\"1\",\"ID_File\":\"10004\"}]";
        JsonParser jsonParser = new JsonParser();
        JsonArray json = new JsonParser().parse(jsonString).getAsJsonArray();

        try {
            file = fileDAO.getFileById(file_id);

            if (file.getUserCreated().getId_User() == user.getId_User() || user.getId_Role() == 2 || user.getId_Role() == 3) {
                if (json.size() == 0) {
                    message.put(ERROR_ATTRIBUTE, "Необходимо выбрать исполнителей");
                } else {
                    for (JsonElement jsonElement : json) {
                        try {
                            users.add(userDAO.getUserById(jsonElement.getAsJsonObject().get("ID_User").getAsLong()));
                        } catch (UserDAOException e) {
                            System.out.println("Ошибка получения пользователя с ID = " + jsonElement.getAsJsonObject().get("ID_User").getAsLong());
                        }
                    }

                    try {
                        new CommonDAO().addFileAccess(file_id, users);
                    } catch (FileAccessDAOException e) {
                        System.out.println("Ошибка добавления записи в таблицу доступа к файлам.asd");

                    }
                }
            }
        } catch (FileDAOException e) {
            System.out.println("Ошибка получения файла с ID = " + file_id);
        }
    }
}
