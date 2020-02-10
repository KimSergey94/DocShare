package kz.itbc.docshare.service;

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

import static kz.itbc.docshare.constants.AppConstant.*;

public class HideUnhideFolderService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(HideUnhideFolderService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException  {
        final HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        int ID_Folder = Integer.parseInt(req.getParameter(ID_PARAMETER));

        FolderDAO folderDAO = new FolderDAO();
        Folder folder = null;
        try {
            folder = folderDAO.getFolderById(ID_Folder);
            System.out.println("ID_Folder "+ID_Folder);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения файла по ID = " + ID_Folder);
        }

        System.out.println("b4 if: "+folder.isFlagHidden());
        if (!folder.isFlagHidden()) {
            folder.setFlagHidden(true);
        } else if (folder.isFlagHidden()) {
            folder.setFlagHidden(false);
        }

        System.out.println("after if: "+folder.isFlagHidden());

        if(  (folder.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  ) {
            try {
                System.out.println("inside if and updating the folder "+folder.getId_Folder());
                folderDAO.updateFolder(folder);
            } catch (FolderDAOException e) {
                SERVICE_LOGGER.error("Ошибка редактирования папки.");
            }
        }

    }
}
