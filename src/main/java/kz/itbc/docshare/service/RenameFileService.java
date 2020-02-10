package kz.itbc.docshare.service;

import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.*;

public class RenameFileService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        final HttpSession session = req.getSession();
        FileDAO fileDAO = new FileDAO();
        File file = null;
        Map<String, String> message = new HashMap<>();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        long ID_File = Long.parseLong(req.getParameter(ID_PARAMETER));
        String fileName = req.getParameter(FILE_NAME_PARAMETER);

        try {
            file = fileDAO.getFileById(ID_File);
            file.setName(fileName);

        } catch (FileDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения файла по ID = " + ID_File);
        }

        if(  (file.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  )
        {
            try {
                fileDAO.updateFile(file);
                message.put(MESSAGE_ATTRIBUTE, "OK");
            } catch (FileDAOException e) {
                SERVICE_LOGGER.error("Ошибка редактирования файла");
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
