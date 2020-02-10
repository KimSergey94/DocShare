package kz.itbc.docshare.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kz.itbc.docshare.database.DAO.DepartmentDAO;
import kz.itbc.docshare.database.DAO.FolderDAO;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static kz.itbc.docshare.constants.AppConstant.*;

public class SortFoldersAJAX implements Service {
    private static final Logger SERVICE_LOGGER = LogManager.getLogger(DownloadFileService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        List<Folder> foldersList = null;
        int sort_id = Integer.parseInt(req.getParameter("sort_id"));
        String order_type = req.getParameter("order_type");
        CurrentPage currentPage = (CurrentPage) session.getAttribute(CURRENT_PAGE_ATTRIBUTE);


        if(currentPage != null) {
            int currentPageId = currentPage.getId();
            Boolean isDepartment = currentPage.isDepartment();
            try {
                if (isDepartment) {
                    foldersList = getFoldersByDepartment(currentPageId);
                    foldersList = getSortedFolder(foldersList, sort_id, order_type);
                } else {
                    switch (currentPageId) {
                        case MYSTORAGE_PAGE_ID: {
                            foldersList = getUserFolders(user.getId_User());
                            foldersList = getSortedFolder(foldersList, sort_id, order_type);
                            break;
                        }
                        case SHARED_PAGE_ID: {
                            foldersList = getSharedDocuments(user.getId_User());
                            foldersList = getSortedFolder(foldersList, sort_id, order_type);
                            break;
                        }
                        case TRASH_PAGE_ID: {
                            foldersList = getDeletedFoldersByUserID(user);
                            foldersList = getSortedFolder(foldersList, sort_id, order_type);
                            break;
                        }
                        case SEARCH_PAGE_ID: {
                            String stringJson = req.getParameter(FOLDER_IDS_PARAMETER);
                            JsonArray json = new JsonParser().parse(stringJson).getAsJsonArray();
                            foldersList = getSearchedFolders(json);
                            foldersList = getSortedFolder(foldersList, sort_id, order_type);
                            break;
                        }
                        default: {
                            RequestDispatcher requestDispatcher = req.getRequestDispatcher(MAIN_PAGE_JSP);
                            requestDispatcher.forward(req, res);
                            break;
                        }
                    }
                }
                res.setContentType(JSON_CONTENT_TYPE);
                res.setCharacterEncoding(UTF_8_CHARSET);
                PrintWriter writer = res.getWriter();
                String jsonResponse = new Gson().toJson(foldersList);
                writer.write(jsonResponse);
            } catch (ServletException e) {
                SERVICE_LOGGER.error("При работе сервлета воозникла ошибка.", e);
            } catch (IOException e) {
                SERVICE_LOGGER.error("Возникла ошибка.", e);
            }
        }
    }


    private List<Folder> getSortedFolder(List<Folder> folders, int sort_id, String order_type) {
        Comparator<Folder> compareByName = Comparator.comparing((Folder folder) -> folder.getName());
        Comparator<Folder> compareByDate = Comparator.comparing((Folder folder) -> folder.getCreationDate().getTime());

        if (sort_id == 1) {
            if (order_type.equals("ASC")) {
                folders.sort(compareByDate);
            } else if (order_type.equals("DESC")) {
                folders.sort(compareByDate.reversed());
            }
        } else if (sort_id == 2) {
            if (order_type.equals("ASC")) {
                folders.sort(compareByName);
            } else if (order_type.equals("DESC")) {
                folders.sort(compareByName.reversed());
            }
        }
        return folders;
    }

    private List<Folder> getFoldersByDepartment(int departmentID){
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> foldersList = null;
        try {
            foldersList = folderDAO.getFoldersByDepartmentID(departmentID);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения папок по отделу с id = " + departmentID, e);
        }
        return foldersList;
    }
    private List<Folder> getUserFolders(long userID){
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> foldersList = null;
        try {
            foldersList = folderDAO.getStorageFoldersByUserID(userID);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения папок пользователя.", e);
        }
        return foldersList;
    }
    private List<Folder> getSharedDocuments(long userID){
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> foldersList = new ArrayList<>();
        try {
            foldersList = folderDAO.getSharedDocumentsByUserID(userID);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения доступных папок пользователя с id = " + userID, e);
        }
        return foldersList;
    }
    private List<Folder> getDeletedFoldersByUserID(User user){
        FolderDAO folderDAO = new FolderDAO();
        List<Folder> foldersList = null;
        try {
            foldersList = folderDAO.getDeletedFoldersByUser(user);
        } catch (FolderDAOException e) {
            SERVICE_LOGGER.error("Ошибка получения удаленных папок пользователем с id = " + user.getId_User(), e);
        }
        return foldersList;
    }
    private List<Folder> getSearchedFolders(JsonArray json){
        List<Folder> foldersList = new ArrayList<>();
        FolderDAO folderDAO = new FolderDAO();
            for (JsonElement jsonElement : json) {
                try {
                    foldersList.add(folderDAO.getFolderById(jsonElement.getAsInt()));
                } catch (FolderDAOException e) {
                    SERVICE_LOGGER.error("Ошибка получения папки с ID = " + jsonElement.toString(), e);
                }
            }
        return foldersList;
    }
}
