package kz.itbc.docshare.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.*;

public class UploadFileOverlayAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect(req.getContextPath() + MAIN_PAGE_URI);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("req.getParameter(folder_id)"+req.getParameter(OPENED_FOLDER_ATTRIBUTE));
        if(req.getParameter(OPENED_FOLDER_ATTRIBUTE) != null) {
            int folderId = Integer.parseInt(req.getParameter(OPENED_FOLDER_ATTRIBUTE));
            HttpSession session = req.getSession();
            session.setAttribute(OPENED_FOLDER_ATTRIBUTE, folderId);
        }
        req.getRequestDispatcher("/WEB-INF/jsp-parts/file-upload-overlay.jsp").forward(req, res);
    }
}