package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FolderDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RenameFolderServiceTesting {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(RenameFolderServiceTesting.class);

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        FolderDAO folderDAO = new FolderDAO();
        User user = null;
        String folderName = "My NEW Folder";

        try {
            user = userDAO.getUserById(1);
        } catch (UserDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения пользователя по ID.");
        }

        try{
            Folder folder = folderDAO.getFolderById(4);
            folder.setName(folderName);
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
