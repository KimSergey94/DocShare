package kz.itbc.docshare.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;
import static kz.itbc.docshare.constants.AppConstant.UTF_8_CHARSET;

public class SortFilesAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        List<File> filesList = null;
        int sort_id = Integer.parseInt(req.getParameter("sort_id"));
        String order_type = req.getParameter("order_type");


        String stringJson = req.getParameter(FILE_IDS_PARAMETER);
        JsonArray json = new JsonParser().parse(stringJson).getAsJsonArray();
        filesList = getSearchedFiles(json);
        filesList = getSortedFiles(filesList, sort_id, order_type);


        res.setContentType(JSON_CONTENT_TYPE);
        res.setCharacterEncoding(UTF_8_CHARSET);
        PrintWriter writer = res.getWriter();
        String jsonResponse = new Gson().toJson(filesList);
        writer.write(jsonResponse);
    }

    private List<File> getSortedFiles(List<File> files, int sort_id, String order_type) {
        Comparator<File> compareByName = Comparator.comparing((File file) -> file.getName());
        Comparator<File> compareByDate = Comparator.comparing((File file) -> file.getCreationDate().getTime());

        if (sort_id == 1) {
            if (order_type.equals("ASC")) {
                files.sort(compareByDate);
            } else if (order_type.equals("DESC")) {
                files.sort(compareByDate.reversed());
            }
        } else if (sort_id == 2) {
            if (order_type.equals("ASC")) {
                files.sort(compareByName);
            } else if (order_type.equals("DESC")) {
                files.sort(compareByName.reversed());
            }
        }
        return files;
    }

    private List<File> getSearchedFiles(JsonArray json){
        List<File> filesList = new ArrayList<>();
        FileDAO folderDAO = new FileDAO();
        File file = null;
        for (JsonElement jsonElement : json) {
            try {
                file = folderDAO.getFileByIdWithoutData(jsonElement.getAsInt());
                filesList.add(file);
            } catch (FileDAOException e) {
                SERVICE_LOGGER.error("Ошибка получения файла с ID = " + jsonElement.toString(), e);
            }
        }
        return filesList;
    }
}