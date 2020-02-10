package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.CommonDAO;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.CommonDAOException;
import kz.itbc.docshare.exception.FileDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;

public class SearchResultsForFilesService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);

        String headerName = "Результаты поиска файлов";
        String keyword = req.getParameter("keyword");
        keyword = "%".concat(keyword).concat("%");
        System.out.println("keyword: "+keyword);

        int filesViewId = TILES_VIEW_ID;
        if(session.getAttribute(FILES_VIEW_ID_ATTRIBUTE) != null){
            filesViewId = Integer.parseInt(session.getAttribute(FILES_VIEW_ID_ATTRIBUTE).toString());
        } else {
            session.setAttribute(FILES_VIEW_ID_ATTRIBUTE, TILES_VIEW_ID);
        }

        if (keyword == EMPTY_SEARCH_BOX_VALUE){
            req.removeAttribute(FILES_ATTRIBUTE);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(FILES_SEARCH_RESULTS_JSP);
            requestDispatcher.forward(req, res);
        }
        try {
            List<File> fileList = new FileDAO().getAllFilesByKeyword(keyword);
            Iterator<File> iterator = new ArrayList<>(fileList).iterator();
            while(iterator.hasNext()) {
                System.out.println("+1");
                File file = iterator.next();
                if(user.getId_Role() == 2 && user.getId_Role() == 3){
                    System.out.println("admin");
                    continue;
                }
                if(file.getUserCreated().getId_User() == user.getId_User()){
                    System.out.println("creator");
                    continue;
                }
                if (file.isFlagHidden()){
                    System.out.println("hidden");
                    fileList.remove(file);
                    continue;
                }
                if (!new CommonDAO().isFileSharedWithUser(user, file)){
                    System.out.println("shared");
                    fileList.remove(file);
                    continue;
                }
            }
            req.setAttribute(FILES_ATTRIBUTE, fileList);
            req.setAttribute("headerName", headerName);
            //req.setAttribute("headerName", headerName);



            if(filesViewId == TILES_VIEW_ID){
                System.out.println("TILES_VIEW_ID");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(FILES_SEARCH_RESULTS_JSP);
                requestDispatcher.forward(req, res);
            } else if (filesViewId == DETAILS_VIEW_ID){
                System.out.println("DETAILS_VIEW_ID");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(FILES_SEARCH_RESULTS_DETAILS_JSP);
                requestDispatcher.forward(req, res);
            } else if (filesViewId == LIST_VIEW_ID){
                System.out.println("LIST_VIEW_ID");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(FILES_SEARCH_RESULTS_LIST_JSP);
                requestDispatcher.forward(req, res);
            }

        } catch (FileDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения файлов по ключевому слову " + keyword, e);
        } catch (CommonDAOException e) {
            SERVICE_LOGGER.error("Ошибка проверки пользователя на доступ к папке", e);
        }


    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}
