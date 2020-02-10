package kz.itbc.docshare.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.FolderDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;

public class LoadFilesAttributesInfoAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getParameter(ID_PARAMETER) != null) {
            try {
                File file = new FileDAO().getFileById(Long.parseLong(req.getParameter(ID_PARAMETER)));
                req.setAttribute("openedFile", file);
                req.setAttribute("parentFolderName", new FolderDAO().getFolderById(file.getId_Folder()).getName());
                int fileSize = file.getData().available() / 1024;
                req.setAttribute("fileSize", fileSize);
            } catch (FileDAOException e) {
                SERVICE_LOGGER.error("Ошибка получения файла по id = " + Long.parseLong(req.getParameter(ID_PARAMETER)), e);
            } catch (FolderDAOException e) {
                SERVICE_LOGGER.error("Ошибка получения родительской папки файла", e);
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOAD_FILES_ATTRIBUTES_INFO_JSP);
            requestDispatcher.forward(req, res);
        } else if (req.getParameter(IDS_PARAMETER) != null) {
            FileDAO fileDAO = new FileDAO();
            String stringJson = req.getParameter(IDS_PARAMETER);
            JsonArray json = new JsonParser().parse(stringJson).getAsJsonArray();
            int filesSize = 0;
            int filesNum = 0;
            for (JsonElement jsonElement : json) {
                try {
                    System.out.println(jsonElement.getAsLong());
                    filesSize += fileDAO.getFileById(jsonElement.getAsLong()).getData().available() / 1024;
                    System.out.println("filesSize: "+filesSize);
                    filesNum++;
                } catch (FileDAOException | JsonSyntaxException e) {
                    SERVICE_LOGGER.error("Ошибка получения файла с ID = " + jsonElement.getAsLong());
                }
            }
            req.setAttribute("filesSize", filesSize);
            req.setAttribute("filesNum", filesNum);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOAD_FILES_ATTRIBUTES_INFO_JSP);
            requestDispatcher.forward(req, res);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOAD_FILES_ATTRIBUTES_INFO_JSP);
            requestDispatcher.forward(req, res);
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
