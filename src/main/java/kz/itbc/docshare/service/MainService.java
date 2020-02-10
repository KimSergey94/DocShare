package kz.itbc.docshare.service;

import kz.itbc.docshare.database.DAO.DepartmentDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.database.DAO.UserDAO;
import kz.itbc.docshare.entity.CurrentPage;
import kz.itbc.docshare.entity.Department;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.DepartmentDAOException;
import kz.itbc.docshare.exception.FolderDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;

public class MainService implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            User user = new UserDAO().getUserById(1);
            session.setAttribute(USER_ATTRIBUTE, user);
        } catch (UserDAOException e) {
            e.printStackTrace();
        }


        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);

        String reqURI = req.getRequestURI();
        reqURI = reqURI.replace(req.getContextPath(), "");
        String queryString = req.getQueryString();

        session = req.getSession();
        List<Department> departments = new ArrayList();

        try {
            departments = new DepartmentDAO().getDepartments();
            req.setAttribute(DEPARTMENTS_ATTRIBUTE, departments);
        } catch (DepartmentDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения отделов", e);
        }

        switch (reqURI) {
            case MYSTORAGE_PAGE_URI: {
                String headerName = "Мое хранилище";
                CurrentPage currentPage = new CurrentPage();
                currentPage.setId(MYSTORAGE_PAGE_ID);
                currentPage.setDepartment(false);
                session.setAttribute(CURRENT_PAGE_ATTRIBUTE, currentPage);
                getUserFolders(req, res, user.getId_User(), headerName);
                break;
            }
            case SHARED_PAGE_URI: {
                String headerName = "Доступные мне";
                CurrentPage currentPage = new CurrentPage();
                currentPage.setId(SHARED_PAGE_ID);
                currentPage.setDepartment(false);
                session.setAttribute(CURRENT_PAGE_ATTRIBUTE, currentPage);
                getSharedDocuments(req, res, user.getId_User(), headerName);
                break;
            }
            case TRASH_PAGE_URI: {
                String headerName = "Корзина";
                CurrentPage currentPage = new CurrentPage();
                currentPage.setId(TRASH_PAGE_ID);
                currentPage.setDepartment(false);
                session.setAttribute(CURRENT_PAGE_ATTRIBUTE, currentPage);
                getDeletedFoldersByUserID(req, res, user, headerName);
                break;
            }
            case MAIN_PAGE_URI: {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher(MAIN_PAGE_JSP);
                requestDispatcher.forward(req, res);
                break;
            }
        }


        if (queryString != null && queryString.contains("id_Department=")) {
            queryString = queryString.replace("id_Department=", "");
            System.out.println("queryString2: " + queryString);
            if (!queryString.isEmpty()) {
                System.out.println("!queryString.isEmpty");
                try {
                    int departmentId = Integer.parseInt(queryString);
                    String headerName = new DepartmentDAO().getDepartmentById(departmentId).getLocaleRU();
                    System.out.println("headerName: " + headerName);

                    CurrentPage currentPage = new CurrentPage();
                    currentPage.setId(departmentId);
                    currentPage.setDepartment(true);
                    session.setAttribute(CURRENT_PAGE_ATTRIBUTE, currentPage);
                    System.out.println("currentPage: " + currentPage.getId() + currentPage.isDepartment());

                    getFoldersByDepartment(req, res, departmentId, headerName);
                    System.out.println("getFoldersByDepartmentID: ");

                } catch (DepartmentDAOException e) {
                    SERVICE_LOGGER.error("Ошибка получения отдела и его названия.", e);
                } catch (NumberFormatException e) {
                    SERVICE_LOGGER.error("Ошибка получения id отдела.", e);
                }
            }
        }
    }

    private void getFoldersByDepartment(HttpServletRequest req, HttpServletResponse res, int departmentID, String headerName) throws IOException, ServletException{
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> foldersList = null;
        try {
            foldersList = folderDAO.getFoldersByDepartmentID(departmentID);
            req.setAttribute(FOLDERS_ATTRIBUTE, foldersList);
            req.setAttribute(DEPARTMENT_ID_PARAMETER, departmentID);

        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения папок по отделу с id = " + departmentID, e);
        }

        req.setAttribute("headerName", headerName);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(MAIN_PAGE_JSP);
        requestDispatcher.forward(req, res);
    }

    //add if user admin -> get all folders
    private void getUserFolders(HttpServletRequest req, HttpServletResponse res, long userID, String headerName) throws IOException, ServletException{
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> foldersList = null;
        try {
            foldersList = folderDAO.getStorageFoldersByUserID(userID);
            req.setAttribute(FOLDERS_ATTRIBUTE, foldersList);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения папок пользователя.", e);
        }

        req.setAttribute("headerName", headerName);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(MAIN_PAGE_JSP);
        requestDispatcher.forward(req, res);
    }
    private void getSharedDocuments(HttpServletRequest req, HttpServletResponse res, long userID, String headerName) throws IOException, ServletException{
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> storageFoldersList = new ArrayList<>();
        try {
            storageFoldersList = folderDAO.getSharedDocumentsByUserID(userID);
            req.setAttribute(FOLDERS_ATTRIBUTE, storageFoldersList);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения доступных папок пользователя с id = " + userID, e);
        }

        req.setAttribute("headerName", headerName);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(MAIN_PAGE_JSP);
        requestDispatcher.forward(req, res);
    }
    private void getDeletedFoldersByUserID(HttpServletRequest req, HttpServletResponse res, User user, String headerName) throws IOException, ServletException{
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> foldersList = null;
        try {
            foldersList = folderDAO.getDeletedFoldersByUser(user);
            req.setAttribute(FOLDERS_ATTRIBUTE, foldersList);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения удаленных папок пользователем с id = " + user.getId_User(), e);
        }

        req.setAttribute("headerName", headerName);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(MAIN_PAGE_JSP);
        requestDispatcher.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

}
