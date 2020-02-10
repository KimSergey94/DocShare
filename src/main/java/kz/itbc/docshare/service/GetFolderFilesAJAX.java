package kz.itbc.docshare.service;

import kz.itbc.docshare.database.ConnectionPoolDBCP;
import kz.itbc.docshare.database.DAO.CommonDAO;
import kz.itbc.docshare.database.DAO.FileDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
import kz.itbc.docshare.entity.CurrentPage;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;

public class GetFolderFilesAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        CommonDAO commonDAO = new CommonDAO();
        FolderDAO folderDAO = new FolderDAO();

        System.out.println("in GetFolderFilesAJAX");
        if(req.getParameter(ID_PARAMETER) != null){
            System.out.println("in getParameter(ID_PARAMETER) != null");
            int folderId = Integer.parseInt(req.getParameter(ID_PARAMETER));
            Folder folder;
            User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
            HttpSession session = req.getSession();
            session.setAttribute(FOLDER_ID_ATTRIBUTE, folderId);
            int filesViewId = TILES_VIEW_ID;
            if(session.getAttribute(FILES_VIEW_ID_ATTRIBUTE) != null){
                filesViewId = Integer.parseInt(session.getAttribute(FILES_VIEW_ID_ATTRIBUTE).toString());
            } else {
                session.setAttribute(FILES_VIEW_ID_ATTRIBUTE, TILES_VIEW_ID);
            }
            try {
                List<File> fileList = new FileDAO().getFilesByFolderId(folderId);
                folder = folderDAO.getFolderById(folderId);
                req.setAttribute(FOLDER_ATTRIBUTE, folder);
                if (session.getAttribute(CURRENT_PAGE_ATTRIBUTE) != null){
                    CurrentPage currentPage = (CurrentPage)req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE);
                    if(currentPage.getId() == MYSTORAGE_PAGE_ID && !currentPage.isDepartment()) {
                        for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {
                            File file = iterator.next();
                            if(file.isFlagDeleted()){
                                iterator.remove();
                                continue;
                            }
                            if (user.getId_Role() == 2 || user.getId_Role() == 3) {
                                continue;
                            }
                            if (file.isFlagHidden() && file.getUserCreated().getId_User() != user.getId_User()) {
                                iterator.remove();
                            }
                        }
                    }
                    if(currentPage.getId() == SHARED_PAGE_ID && !currentPage.isDepartment()){
                        for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {
                            File file = iterator.next();
                            if(file.isFlagDeleted()){
                                iterator.remove();
                                continue;
                            }
                            if (file.getUserCreated().getId_User() == user.getId_User()) {
                                continue;
                            }
                            if(!commonDAO.isFolderSharedByUser(user, folder, ConnectionPoolDBCP.getInstance().getConnection())
                                    && !commonDAO.isFileSharedByUser(user, file)){
                                iterator.remove();
                                continue;
                            }
                            if (user.getId_Role() == 2 || user.getId_Role() == 3) {
                                continue;
                            }
                            if (file.isFlagHidden() && (file.getUserCreated().getId_User() != user.getId_User())) {
                                iterator.remove();
                                continue;
                            }
                        }
                    }
                    if(currentPage.getId() == TRASH_PAGE_ID && !currentPage.isDepartment()){
                        fileList = new FileDAO().getDeletedFilesByFolderID(user, folderId);
                    }
                    if(currentPage.isDepartment()){
                        Iterator<File> iterator = new ArrayList<>(fileList).iterator();
                        while(iterator.hasNext()) {
                            File file = iterator.next();
                            if(file.isFlagDeleted()){
                                fileList.remove(file);
                                continue;
                            }
                            if((user.getId_User() != file.getUserCreated().getId_User() && file.isFlagHidden()) ||
                                    (user.getId_Role() != 2 || user.getId_Role() != 3) && file.isFlagHidden()){
                                fileList.remove(file);
                            }
                        }
                    }
                } else{
                    /**
                     * Current page == null
                     *
                     */

                    for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {
                        File file = iterator.next();
                        if (file.getUserCreated().getId_User() == user.getId_User()) {
                            continue;
                        }
                        if (user.getId_Role() == 2 || user.getId_Role() == 3) {
                            continue;
                        }
                        if(!commonDAO.isFileSharedByUser(user, file) &&
                                !commonDAO.isFolderSharedByUser(user, new FolderDAO().getFolderById(file.getId_Folder()), ConnectionPoolDBCP.getInstance().getConnection())){
                            iterator.remove();
                            continue;
                        }
                        if (file.isFlagHidden() && (file.getUserCreated().getId_User() != user.getId_User())) {
                            iterator.remove();
                            continue;
                        }
                    }
                }

                req.setAttribute(FILES_ATTRIBUTE, fileList);
                req.setAttribute("headerName", folder.getName());

                System.out.println("filesViewId: "+filesViewId);
                if(filesViewId == TILES_VIEW_ID){
                    System.out.println("TILES_VIEW_ID");
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher(FILES_JSP);
                    requestDispatcher.forward(req, res);
                } else if (filesViewId == DETAILS_VIEW_ID){
                    System.out.println("DETAILS_VIEW_ID");
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher(FILES_DETAILS_JSP);
                    requestDispatcher.forward(req, res);
                } else if (filesViewId == LIST_VIEW_ID){
                    System.out.println("LIST_VIEW_ID");
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher(FILES_LIST_JSP);
                    requestDispatcher.forward(req, res);
                }
            } catch (FileDAOException e) {
                SERVICE_LOGGER.error("Ошибка получения файлов папки с id = " + folderId, e);
            } catch (FolderDAOException e) {
                SERVICE_LOGGER.error("Ошибка получения папки с id = " + folderId, e);
            } catch (FileAccessDAOException e) {
                SERVICE_LOGGER.error("Ошибка получения записи в таблице доступа к файлам ", e);
            } catch (FolderAccessDAOException e) {
                SERVICE_LOGGER.error("Ошибка получения записи в таблице доступа к папкам ", e);
            } catch (SQLException e) {
                SERVICE_LOGGER.error("Ошибка соединения", e);
            }
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        doGet(req, res);
    }
}
