package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.core.util.JsonUtils;

public class RestoreFileServiceTesting {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        FileDAO fileDAO = new FileDAO();
        User user = null;
        try {
            user = userDAO.getUserById(3);
            File file = fileDAO.getFileById(3);
            file.setFlagDeleted(false);
            file.setUserDeleted(null);

            if ((file.getUserCreated().getId_User() == user.getId_User()) || (user.getId_Role() == 2 || user.getId_Role() == 3)) {
                fileDAO.updateFile(file);
            }

        } catch (UserDAOException e) {
            System.out.println("Ошибка получения пользователя");
        } catch (FileDAOException e) {
            System.out.println("Ошибка получения файла по ID");
        }
    }
}
