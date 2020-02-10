package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;

public class DownloadFileService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doPost(req, res);


    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        File file = null;
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        long file_id;
        try {
            file_id = Long.parseLong(req.getParameter(ID_PARAMETER));
        } catch (NumberFormatException e) {
            file_id = -1;
        }

        try {
            file = new FileDAO().getFileById(file_id);
        } catch (FileDAOException e) {
            SERVICE_LOGGER.error("Ошибка при получении файла.");
        }
        assert file != null;
        if (file.getUserCreated().getId_User() == user.getId_User() || user.getId_User() == 2 || user.getId_User() == 3) {
            res.setContentType("APPLICATION/OCTET-STREAM");
            res.setHeader("Content-disposition", "attachment; filename=\"" + (file.getName()) + "\";charset=utf-8");  //new Translit().cyr2lat(  )
            res.setHeader("Cache-Control", "no-cache");
            res.setHeader("Set-Cookie", "HttpOnly;Secure;SameSite=Strict");
            res.setHeader("Expires", "-1");
            OutputStream outStream = res.getOutputStream();
            int numBytesRead;
            while ((numBytesRead = file.getData().read()) != -1) {
                outStream.write(numBytesRead);
            }
            outStream.close();
        }
        else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(MAIN_PAGE_URI);
            requestDispatcher.forward(req, res);
        }

    }
}
