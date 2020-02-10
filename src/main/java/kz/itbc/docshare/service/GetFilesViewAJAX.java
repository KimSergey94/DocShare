package kz.itbc.docshare.service;

import com.google.gson.Gson;
import kz.itbc.docshare.entity.CurrentPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static kz.itbc.docshare.constants.AppConstant.*;

public class GetFilesViewAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String filesViewID = req.getParameter(FILES_VIEW_ID_PARAMETER);
        CurrentPage currentPage = (CurrentPage) session.getAttribute(CURRENT_PAGE_ATTRIBUTE);
        Map<String, String> message = new HashMap<>();
        System.out.println("in getfilesview");

        if(filesViewID.equals("1")){
            session.setAttribute(FILES_VIEW_ID_ATTRIBUTE, TILES_VIEW_ID);
        } else if (filesViewID.equals("2")){
            session.setAttribute(FILES_VIEW_ID_ATTRIBUTE, DETAILS_VIEW_ID);
        } else if(filesViewID.equals("3")){
            session.setAttribute(FILES_VIEW_ID_ATTRIBUTE, LIST_VIEW_ID);
        }

        if(currentPage.getId() == SEARCH_PAGE_ID && !currentPage.isDepartment()){
            message.put(MESSAGE_ATTRIBUTE, "getFilesFromThePage");
            res.setContentType(JSON_CONTENT_TYPE);
            res.setCharacterEncoding(UTF_8_CHARSET);
            PrintWriter writer = res.getWriter();
            String jsonResponse = new Gson().toJson(message);
            writer.write(jsonResponse);
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res);
    }
}
