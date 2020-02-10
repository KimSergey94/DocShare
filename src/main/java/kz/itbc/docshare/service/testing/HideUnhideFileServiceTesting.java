package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;

public class HideUnhideFileServiceTesting {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User user = null;

        try {
            user = userDAO.getUserById(1);
        } catch (UserDAOException e) {
            System.out.println("Ошибка получения пользователя по ID.");
        }

        FileDAO fileDAO = new FileDAO();
        File file = null;
        try {
            file = fileDAO.getFileById(1);
        } catch (FileDAOException e) {
            System.out.println("Ошибка получения файла по ID = " + 1);
        }

        if (file.isFlagHidden())
            file.setFlagHidden(false);
        else
            file.setFlagHidden(true);

        if(  (file.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  )
        {
            try {
                fileDAO.updateFile(file);
            } catch (FileDAOException e) {
                System.out.println("Ошибка редактирования файла");
            }
        }

    }
}
