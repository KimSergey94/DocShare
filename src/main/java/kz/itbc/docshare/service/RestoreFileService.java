package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.*;

public class RestoreFileService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DeleteFolderService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final HttpSession session = req.getSession();
        FileDAO fileDAO = new FileDAO();
        File file = null;

        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        long ID_File;
        try {
            ID_File = Long.parseLong(req.getParameter(ID_PARAMETER));
        } catch (NumberFormatException e) {
            ID_File = -1;
        }

        try {
            file = fileDAO.getFileById(ID_File);
        } catch (FileDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения файла по ID = " + ID_File);
        }

        if(file.getUserCreated().getId_User() == user.getId_User() || user.getId_Role() == 2 || user.getId_Role() == 3 ||
                (file.getUserDeleted() != null && file.getUserDeleted().getId_User() == user.getId_User()))
        {
            try {
                System.out.println("trying ");
                file.setFlagDeleted(false);
                file.setUserDeleted(null);
                fileDAO.updateFile(file);
            } catch (FileDAOException e) {
                SERVICE_LOGGER.error("Ошибка редактирования файла");
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res);
    }
}
