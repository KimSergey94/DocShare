package kz.itbc.docshare.service;

import com.google.gson.Gson;
import kz.itbc.docshare.database.DAO.CommonDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.entity.CurrentPage;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.CommonDAOException;
import kz.itbc.docshare.exception.FolderDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kz.itbc.docshare.constants.AppConstant.*;

public class SearchResultsService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        CurrentPage currentPage = new CurrentPage();
        currentPage.setDepartment(false);
        currentPage.setId(SEARCH_PAGE_ID);
        session.setAttribute(CURRENT_PAGE_ATTRIBUTE, currentPage);
        List<Folder> allFolders = new ArrayList();
        String keyword = req.getParameter("keyword");
        keyword = "%".concat(keyword).concat("%");

        if (keyword == EMPTY_SEARCH_BOX_VALUE){
            req.removeAttribute(FOLDERS_ATTRIBUTE);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(SEARCH_RESULTS_JSP);
            requestDispatcher.forward(req, res);
        }
        try {
            allFolders = new FolderDAO().getAllFoldersByKeyword(keyword);
            for(Folder folder : allFolders){
                if(user.getId_Role() == 2 && user.getId_Role() == 3){
                    continue;
                }
                if(folder.getUserCreated().getId_User() == user.getId_User()){
                    continue;
                }
                if (folder.isFlagHidden()){
                    System.out.println("search result if flagHidden: "+folder.isFlagHidden());
                    allFolders.remove(folder);
                    continue;
                }
                if (!new CommonDAO().isFolderSharedOrUploadedByUser(user, folder)){
                    allFolders.remove(folder);
                    continue;
                }
            }
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения папок", e);
        } catch (CommonDAOException e) {
            SERVICE_LOGGER.error("Ошибка проверки пользователя на доступ к папке", e);
        }

        req.setAttribute(FOLDERS_ATTRIBUTE, allFolders);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(SEARCH_RESULTS_JSP);
        requestDispatcher.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}
