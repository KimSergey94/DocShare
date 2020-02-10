package kz.itbc.docshare.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.OPENED_FILES_PARAMETER;
import static kz.itbc.docshare.constants.AppConstant.USER_ATTRIBUTE;

public class EraseFilesService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        final HttpSession session = req.getSession();
        List<File> files = new ArrayList<>();
        FileDAO fileDAO = new FileDAO();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        String stringJson = req.getParameter(OPENED_FILES_PARAMETER);
        JsonArray json = new JsonParser().parse(stringJson).getAsJsonArray();

        try {
            for (JsonElement jsonElement : json) {
                try {
                    files.add(fileDAO.getFileById(jsonElement.getAsLong()));
                } catch (FileDAOException | JsonSyntaxException e) {
                    SERVICE_LOGGER.error("Ошибка получения файла с ID = " + jsonElement.getAsLong());
                }
            }

            for (File file : files) {
                file.setFlagErased(true);
                file.setUserErased(user);
                fileDAO.eraseFile(file);
            }
        } catch (FileDAOException e) {
            SERVICE_LOGGER.error("Ошибка удаления файла из корзины", e);
        }
    }
}