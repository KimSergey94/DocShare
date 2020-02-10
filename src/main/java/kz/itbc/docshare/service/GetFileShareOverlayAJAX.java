package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.exception.FileDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.ID_PARAMETER;
import static kz.itbc.docshare.constants.AppConstant.MAIN_PAGE_URI;

public class GetFileShareOverlayAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect(req.getContextPath() + MAIN_PAGE_URI);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        long file_id = Long.parseLong(req.getParameter(ID_PARAMETER));
        File file = null;
        try {
            file = new FileDAO().getFileById(file_id);
        } catch (FileDAOException e){
            SERVICE_LOGGER.error("Ошибка получения файла с ID = " + file_id);
        }
        req.setAttribute("file", file);
        req.getRequestDispatcher("/WEB-INF/jsp-parts/file-share-overlay.jsp").forward(req, res);
    }
}
