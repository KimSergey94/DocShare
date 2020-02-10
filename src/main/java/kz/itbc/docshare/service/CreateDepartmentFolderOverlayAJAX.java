package kz.itbc.docshare.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kz.itbc.docshare.constants.AppConstant.DEPARTMENT_ID_PARAMETER;
import static kz.itbc.docshare.constants.AppConstant.MAIN_PAGE_URI;

public class CreateDepartmentFolderOverlayAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect(req.getContextPath() + MAIN_PAGE_URI);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp-parts/department-folder-create-overlay.jsp").forward(req, res);
    }
}
