package kz.itbc.docshare.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
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
import java.util.zip.ZipOutputStream;

import static kz.itbc.docshare.constants.AppConstant.*;

public class DownloadFilesService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        final HttpSession session = req.getSession();
        List<File> files = new ArrayList<>();
        FileDAO fileDAO = new FileDAO();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        String stringJson = req.getParameter(OPENED_FILES_PARAMETER);
        JsonArray json = new JsonParser().parse(stringJson).getAsJsonArray();

        for (JsonElement jsonElement : json) {
            try {
                files.add(fileDAO.getFileById(jsonElement.getAsLong()));
            } catch (FileDAOException | JsonSyntaxException e) {
                SERVICE_LOGGER.error("Ошибка получения файла с ID = " + jsonElement.getAsLong());
            }
        }
/*
        File zipFile = new File();
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));

        */

        for (File file : files) {
            assert file != null;
            if (file.getUserCreated().getId_User() == user.getId_User() || user.getId_User() == 2 || user.getId_User() == 3) {
                res.setContentType("APPLICATION/OCTET-STREAM");
                res.setHeader("Content-disposition", "attachment; filename=\"" + ("Files Archive") + "\";charset=utf-8");  //new Translit().cyr2lat(  )
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
}