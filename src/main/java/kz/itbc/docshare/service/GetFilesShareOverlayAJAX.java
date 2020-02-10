package kz.itbc.docshare.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.exception.FileDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static kz.itbc.docshare.constants.AppConstant.MAIN_PAGE_URI;
import static kz.itbc.docshare.constants.AppConstant.OPENED_FILES_PARAMETER;

public class GetFilesShareOverlayAJAX implements Service{
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.sendRedirect(req.getContextPath() + MAIN_PAGE_URI);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp-parts/files-share-overlay.jsp").forward(req, res);
    }
}
