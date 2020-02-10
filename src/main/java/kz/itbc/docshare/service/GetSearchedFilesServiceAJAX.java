package kz.itbc.docshare.service;

import com.google.gson.*;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.exception.FileDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.*;

public class GetSearchedFilesServiceAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Map<String, String> message = new HashMap<>();
        List<File> files = new ArrayList<>();
        FileDAO fileDAO = new FileDAO();
        String stringJsonOpenedFiles = req.getParameter(SEARCH_RESULT_FILES_PARAMETER);
        JsonArray jsonFiles = new JsonParser().parse(stringJsonOpenedFiles).getAsJsonArray();

        try {
            for (JsonElement jsonElement : jsonFiles) {
                try {
                    files.add(fileDAO.getFileByIdWithoutData(jsonElement.getAsLong()));
                } catch (FileDAOException | JsonSyntaxException e) {
                    SERVICE_LOGGER.error("Ошибка получения файла с ID = " + jsonElement.getAsLong());
                }
            }
        } catch (Exception e) {
            SERVICE_LOGGER.error("Возникла ошибка",e);
            message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
        }
        res.setContentType(JSON_CONTENT_TYPE);
        res.setCharacterEncoding(UTF_8_CHARSET);
        PrintWriter writer = res.getWriter();
        String jsonResponse = new Gson().toJson(files);
        writer.write(jsonResponse);
    }
}
