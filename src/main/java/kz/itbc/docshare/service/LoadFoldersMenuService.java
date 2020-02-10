package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.exception.FolderDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.*;

public class LoadFoldersMenuService extends HttpServlet{
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            req.setAttribute("openedFolder", new FolderDAO().getFolderById(Integer.parseInt(req.getParameter(ID_PARAMETER))));
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения папки по id = " + Integer.parseInt(req.getParameter(ID_PARAMETER)), e);
        }
        String isTrash = req.getParameter(IS_TRASH_PARAMETER);
        req.setAttribute(IS_TRASH_PARAMETER, isTrash);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(LOAD_FOLDERS_MENU_JSP);
        requestDispatcher.include(req, res);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
