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

import static kz.itbc.docshare.constants.AppConstant.*;

public class RestoreFilesService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DeleteFolderService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<File> files = new ArrayList<>();
        FileDAO fileDAO = new FileDAO();
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
                file.setFlagDeleted(false);
                file.setUserDeleted(null);
                fileDAO.updateFile(file);
            }
        } catch (FileDAOException e) {
            SERVICE_LOGGER.error("Ошибка удаления файла", e);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res);
    }
}
