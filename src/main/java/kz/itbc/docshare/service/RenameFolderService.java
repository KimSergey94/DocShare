package kz.itbc.docshare.service;

import com.google.gson.Gson;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FolderDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.*;

public class RenameFolderService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(RenameFolderService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        Map<String, String> message = new HashMap<>();
        int ID_Folder = Integer.parseInt(req.getParameter(ID_PARAMETER));
        String folderName = req.getParameter(FOLDER_NAME_PARAMETER);
        FolderDAO folderDAO = new FolderDAO();
        Folder folder = null;
        try {
            folder = folderDAO.getFolderById(ID_Folder);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения файла по ID = " + ID_Folder);
        }
        folder.setName(folderName);

        if(  (folder.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  ) {
            try {
                folderDAO.updateFolder(folder);
                message.put(MESSAGE_ATTRIBUTE, "OK");
            } catch (FolderDAOException e) {
                SERVICE_LOGGER.error("Ошибка редактирования папки.");
                message.put(ERROR_ATTRIBUTE, "error");
            }
        }
        res.setContentType(JSON_CONTENT_TYPE);
        res.setCharacterEncoding(UTF_8_CHARSET);
        PrintWriter writer = res.getWriter();
        String json = new Gson().toJson(message);
        writer.write(json);
    }
}
