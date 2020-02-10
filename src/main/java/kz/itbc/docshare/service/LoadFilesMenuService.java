package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.exception.FileDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.*;

public class LoadFilesMenuService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            req.setAttribute("openedFile", new FileDAO().getFileById(Long.parseLong(req.getParameter(ID_PARAMETER))));
            File file = (File)req.getAttribute("openedFile");
            System.out.println("THE FILE ID: " + file.getId_File());
        } catch (FileDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения файла по id = " + Long.parseLong(req.getParameter(ID_PARAMETER)), e);
        }
        String isTrash = req.getParameter(IS_TRASH_PARAMETER);
        req.setAttribute(IS_TRASH_PARAMETER, isTrash);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOAD_FILES_MENU_JSP);
        requestDispatcher.forward(req, res);
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
