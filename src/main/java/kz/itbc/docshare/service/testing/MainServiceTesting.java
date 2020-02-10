package kz.itbc.docshare.service.testing;

import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.exception.FolderDAOException;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class MainServiceTesting {
    public static void main(String[] args) {
        String reqURI = "/department?id_Department=";

        if (reqURI.contains("/department?id_Department="))
        {
            reqURI = reqURI.replace("/department?id_Department=", "");

            if(reqURI.isEmpty()) {
                System.out.println("asdasdasd");
            }
        }


        System.out.println("folders.size");

    }
}
