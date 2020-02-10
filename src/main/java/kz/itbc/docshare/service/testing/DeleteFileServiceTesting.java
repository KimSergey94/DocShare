package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.CurrentPage;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.*;

import static kz.itbc.docshare.constants.AppConstant.*;

public class DeleteFileServiceTesting {
    private static final Logger DAO_LOGGER = LogManager.getLogger(DeleteFileServiceTesting.class);

    public static void main(String[] args) throws UserDAOException, FileDAOException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(1);
        List<File> fileList = new FileDAO().getFilesByFolderId(1);
        System.out.println(fileList.size());

        CurrentPage currentPage = new CurrentPage();
        currentPage.setId(MYSTORAGE_PAGE_ID);
        currentPage.setDepartment(false);

        if (currentPage.getId() == MYSTORAGE_PAGE_ID && !currentPage.isDepartment()) {
            System.out.println("asdasdwqe");
            for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {
                File file = iterator.next();
                System.out.println("1asd");
                if (user.getId_Role() == 2 || user.getId_Role() == 3) {
                    System.out.println(2);
                    continue;
                }
                if (file.isFlagHidden() && file.getUserCreated().getId_User() != user.getId_User()) {
                    System.out.println(3);
                    iterator.remove();
                    continue;
                }
                if (file.isFlagDeleted()) {
                    System.out.println(4);
                    iterator.remove();
                    continue;
                }
            }
        }
    }
}
