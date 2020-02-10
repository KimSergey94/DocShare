package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.CommonDAO;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.FolderDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.ID_PARAMETER;
import static kz.itbc.docshare.constants.AppConstant.USER_ATTRIBUTE;

public class EraseFolderService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(EraseFolderService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException  {
        doPost(req, res);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException  {
        final HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        int ID_Folder = Integer.parseInt(req.getParameter(ID_PARAMETER));
        FolderDAO folderDAO = new FolderDAO();
        FileDAO fileDAO = new FileDAO();
        Folder folder = null;
        try {
            folder = folderDAO.getFolderById(ID_Folder);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения файла по ID = " + ID_Folder);
        }
        folder.setFlagErased(true);
        folder.setUserErased(user);

        if(  (folder.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  ) {
            try {
                folderDAO.eraseFolder(folder);
            } catch (FolderDAOException e) {
                SERVICE_LOGGER.error("Ошибка удаления папки с корзины.");
            } catch (FileDAOException e) {
                SERVICE_LOGGER.error("Ошибка удаления файлов папки с корзины.");
            }
        }

    }
}
