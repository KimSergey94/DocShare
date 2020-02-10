package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FolderDAOException;
import kz.itbc.docshare.exception.UserDAOException;

public class HideUnhideFolderServiceTesting {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User user = null;

        try {
            user = userDAO.getUserById(1);
        } catch (UserDAOException e) {
            System.out.println("Ошибка получения пользователя по ID.");
        }
;
        FolderDAO folderDAO = new FolderDAO();
        Folder folder = null;
        try {
            folder = folderDAO.getFolderById(1);
        } catch (FolderDAOException e) {
            System.out.println("Ошибка получения файла по ID = " + 1);
        }

        if (folder.isFlagHidden())
            folder.setFlagHidden(false);
        else
            folder.setFlagHidden(true);

        if(  (folder.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  ) {
            try {
                folderDAO.updateFolder(folder);
            } catch (FolderDAOException e) {
                System.out.println("Ошибка редактирования папки.");
            }
        }
    }
}
