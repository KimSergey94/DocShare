package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.User;

public class CreateFolderServiceTesting {
    public static void main(String[] args)throws Exception {
        User user = new UserDAO().getUserById(1);
        String folderName = "asdasdasd";
        int ID_Department = 1;
        boolean flagHidden = true;

        FolderDAO folderDAO = new FolderDAO();
        //folderDAO.addFolder(folderName, ID_Department, flagHidden, user);
    }
}
