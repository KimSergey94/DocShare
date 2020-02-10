package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.FolderDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.*;

public class GetFolderShareOverlayAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect(req.getContextPath() + MAIN_PAGE_URI);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int folder_id = Integer.parseInt(req.getParameter(ID_PARAMETER));
        Folder folder = null;
        try {
            folder = new FolderDAO().getFolderById(folder_id);
        } catch (FolderDAOException e){
            SERVICE_LOGGER.error("Ошибка получения папки с ID = " + folder_id);
        }
        req.setAttribute("folder", folder);
        req.getRequestDispatcher("/WEB-INF/jsp-parts/folder-share-overlay.jsp").forward(req, res);
    }
}
