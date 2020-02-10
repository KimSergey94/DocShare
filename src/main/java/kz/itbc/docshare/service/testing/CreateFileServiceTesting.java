package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateFileServiceTesting {
    public static void main(String[] args) throws UserDAOException, FileDAOException {
        User user = new UserDAO().getUserById(1);
        String fileName = "4!";

        InputStream data;
        try {
            File initialFile = new File("src/main/resources/sampleData.txt");
            data = new FileInputStream(initialFile);
        } catch (IOException | NullPointerException e){
            data = null;
        }

        boolean flagHidden = false;
        boolean flagDeleted = false;
        int ID_FileType = 4;
        int ID_Folder = 2;

        FileDAO fileDAO = new FileDAO();
//        fileDAO.addFile("5!", data, flagHidden, flagDeleted, 5, ID_Folder, user);
  //      fileDAO.addFile("6!", data, flagHidden, flagDeleted, 6, ID_Folder, user);
    //    fileDAO.addFile("7!", data, flagHidden, flagDeleted, 7, ID_Folder, user);
      //  fileDAO.addFile("8!", data, flagHidden, flagDeleted, 8, ID_Folder, user);
        //fileDAO.addFile("9!", data, flagHidden, flagDeleted, 9, ID_Folder, user);
//        fileDAO.addFile("10!", data, flagHidden, flagDeleted, 10, ID_Folder, user);
  //      fileDAO.addFile("11!", data, flagHidden, flagDeleted, 11, ID_Folder, user);
    //    fileDAO.addFile("12!", data, flagHidden, flagDeleted, 12, ID_Folder, user);
      //  fileDAO.addFile("13!", data, flagHidden, flagDeleted, 13, ID_Folder, user);

    }
}
