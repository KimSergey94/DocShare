package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FolderDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DeleteFolderServiceTesting {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DeleteFolderServiceTesting.class);

    public static void main(String[] args)  {
        UserDAO userDAO = new UserDAO();
        FolderDAO folderDAO = new FolderDAO();
        User user = null;

        try {
            user = userDAO.getUserById(2);
            user.setId_Role(2);
        } catch (UserDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения пользователя по ID.");
        }

        try{
            Folder folder = folderDAO.getFolderById(2);
            folder.setFlagDeleted(true);
            folder.setUserDeleted(user);
            try{
                if(  (folder.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  )
                {
                    folderDAO.updateFolder(folder);
                }
            } catch (FolderDAOException e) {
                SERVICE_LOGGER.error("Ошибка редактирования папки.");
            }
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения папки по ID = 6.");
        }
    }
}
