package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FolderDAOException;
import kz.itbc.docshare.exception.UserDAOException;

public class RestoreFolderServiceTesting {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        FolderDAO folderDAO = new FolderDAO();
        User user = null;

        try {
            user = userDAO.getUserById(1);
        } catch (UserDAOException e) {
            System.out.println("Ошибка получения пользователя по ID.");
        }

        try{
            Folder folder = folderDAO.getFolderById(3);
            folder.setFlagDeleted(false);
            folder.setUserDeleted(null);
            try{
                if(  (folder.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  )
                {
                    folderDAO.updateFolder(folder);
                }
            } catch (FolderDAOException e) {
                System.out.println("Ошибка редактирования папки.");
            }
        } catch (FolderDAOException e) {
            System.out.println("Ошибка получения папки по ID = 6.");
        }
    }
}
