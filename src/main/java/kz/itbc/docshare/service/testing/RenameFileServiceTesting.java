package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;

public class RenameFileServiceTesting {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        FileDAO folderDAO = new FileDAO();
        User user = null;
        String folderName = "My NEW File";

        try {
            user = userDAO.getUserById(1);
        } catch (UserDAOException e) {
            System.out.println("Ошибка получения пользователя по ID.");
        }

        try{
            File file = folderDAO.getFileById(1);
            file.setName(folderName);
            try{
                if(  (file.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)  )
                {
                    folderDAO.updateFile(file);
                }
            } catch (FileDAOException e) {
                System.out.println("Ошибка редактирования папки.");
            }
        } catch (FileDAOException e) {
            System.out.println("Ошибка получения папки по ID = 6.");
        }
    }
}