package kz.itbc.docshare.service;

import com.google.gson.Gson;
import kz.itbc.docshare.entity.CurrentPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.*;
import static kz.itbc.docshare.constants.AppConstant.UTF_8_CHARSET;

public class CheckIfSearchedFilesAJAX implements Service {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        CurrentPage currentPage = (CurrentPage) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE);
        Map<String, String> message = new HashMap<>();

        if(currentPage != null){
            if(!currentPage.isDepartment() && currentPage.getId() == SEARCH_PAGE_ID){
                message.put(CURRENT_PAGE_ATTRIBUTE, "searchedFiles");
            } else{
                message.put(CURRENT_PAGE_ATTRIBUTE, "notSearchedFiles");

            }
        } else{
            message.put(CURRENT_PAGE_ATTRIBUTE, "isNull");
        }

        res.setContentType(JSON_CONTENT_TYPE);
        res.setCharacterEncoding(UTF_8_CHARSET);
        PrintWriter writer = res.getWriter();
        String jsonResponse = new Gson().toJson(message);
        writer.write(jsonResponse);
    }
}