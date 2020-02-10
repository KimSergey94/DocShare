package kz.itbc.docshare.service;

import com.google.gson.*;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
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

public class LoadManyFilesMenuService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        List<File> files = new ArrayList<>();
        FileDAO fileDAO = new FileDAO();
        Boolean allFilesVisible = true;
        Boolean isUserFileUploader = true;
        String isTrash = req.getParameter(IS_TRASH_PARAMETER);
        String stringJson = req.getParameter(FILES_PARAMETER);
        JsonArray json = new JsonParser().parse(stringJson).getAsJsonArray();

        if(json.size() == 1){
            try {
                req.setAttribute("openedFile", new FileDAO().getFileById(json.get(0).getAsLong()));
                File file = (File)req.getAttribute("openedFile");
                System.out.println("THE FILE ID: " + file.getId_File());
            } catch (FileDAOException e) {
                SERVICE_LOGGER.error("Ошибка получения файла по id = " + Long.parseLong(req.getParameter(ID_PARAMETER)), e);
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOAD_FILES_MENU_JSP);
            requestDispatcher.forward(req, res);
        } else{
            try {
                for (JsonElement jsonElement : json) {
                    try {
                        System.out.println(jsonElement.getAsLong());
                        files.add(fileDAO.getFileById(jsonElement.getAsLong()));
                    } catch (FileDAOException | JsonSyntaxException e) {
                        SERVICE_LOGGER.error("Ошибка получения файла с ID = " + jsonElement.getAsLong());
                    }
                }

                for(File file : files){
                    if(file.isFlagHidden()){
                        allFilesVisible = false;
                    }
                    if(file.getUserCreated().getId_User() != user.getId_User()){
                        isUserFileUploader = false;
                    }
                }
                req.setAttribute("allFilesVisible", allFilesVisible);
                req.setAttribute("isUserFileUploader", isUserFileUploader);
                req.setAttribute(IS_TRASH_PARAMETER, isTrash);
                req.setAttribute("openedFiles", files);
            } catch (Exception e) {
                SERVICE_LOGGER.error("Возникла ошибка");
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOAD_MANY_FILES_MENU_JSP);
            requestDispatcher.forward(req, res);
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
