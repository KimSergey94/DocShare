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

public class HideUnhideFileService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(HideUnhideFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        final HttpSession session = req.getSession();
        FileDAO fileDAO = new FileDAO();
        File file = null;

        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        long ID_File = Long.parseLong(req.getParameter(ID_PARAMETER));

        try {
            file = fileDAO.getFileById(ID_File);
        } catch (FileDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения файла по ID = " + ID_File);
        }

        assert file != null;
        if (file.isFlagHidden())
            file.setFlagHidden(false);
        else
            file.setFlagHidden(true);

        if(  (file.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  )
        {
            try {
                fileDAO.updateFile(file);
            } catch (FileDAOException e) {
                SERVICE_LOGGER.error("Ошибка редактирования файла");
            }
        }

    }
}
