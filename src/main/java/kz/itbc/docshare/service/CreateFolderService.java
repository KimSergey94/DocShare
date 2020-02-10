package kz.itbc.docshare.service;

import com.google.gson.Gson;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FolderDAOException;
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


public class CreateFolderService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        final HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        String folder_name = req.getParameter(FOLDER_NAME_PARAMETER);
        FolderDAO folderDAO = new FolderDAO();
        Map<String, String> message = new HashMap<>();
        boolean flagHidden = Boolean.parseBoolean(req.getParameter(FLAG_HIDDEN_PARAMETER));

        try {
            if(folderDAO.userFolderExists(user, folder_name)){
                message.put(ERROR_ATTRIBUTE, "Папка с таким названием уже существует");
            } else{
                try {
                    new FolderDAO().addStorageFolder(folder_name, flagHidden, user);
                    message.put(MESSAGE_ATTRIBUTE, "Вы успешно создали папку");
                } catch (FolderDAOException e){
                    SERVICE_LOGGER.error("Ошибка создания папки");
                    message.put(ERROR_ATTRIBUTE, "Папка с таким названием уже существует");
                }
            }
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка проверки наличии папки пользователя с указаным названием.");
            message.put(ERROR_ATTRIBUTE, "Возникла ошибка");
        }
        res.setContentType(JSON_CONTENT_TYPE);
        res.setCharacterEncoding(UTF_8_CHARSET);
        PrintWriter writer = res.getWriter();
        String jsonResponse = new Gson().toJson(message);
        writer.write(jsonResponse);
    }
}
