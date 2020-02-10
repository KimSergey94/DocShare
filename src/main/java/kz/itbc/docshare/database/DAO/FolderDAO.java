package kz.itbc.docshare.database.DAO;

import kz.itbc.docshare.database.ConnectionPoolDBCP;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.FolderDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static kz.itbc.docshare.constants.SQLConstant.*;

public class FolderDAO {
    private static final BasicDataSource CONNECTION = ConnectionPoolDBCP.getInstance();
    private static final Logger DAO_LOGGER = LogManager.getRootLogger();


    public List<Folder> getAllFoldersByKeyword(String keyword) throws FolderDAOException {
        Folder folder = null;
        List<Folder> folders = new ArrayList<>();
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FOLDERS_BY_KEYWORD_SQL_QUERY)) {
            preparedStatement.setString(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                folder = initializeFolder(resultSet, connection);
                folders.add(folder);
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения папок по ключевому слову", e1);
            throw new FolderDAOException("Ошибка получения папок по ключевому слову", e1);
        }
        return folders;
    }


    public Folder getFolderById(int id) throws FolderDAOException {
        Folder folder = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FOLDER_BY_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                folder = initializeFolder(resultSet, connection);
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения папки", e1);
            throw new FolderDAOException("Ошибка получения папки", e1);
        }

        if(folder == null){
            DAO_LOGGER.info("Папка с ID " + id + " не найдена");
            throw new FolderDAOException("Папка с ID " + id + " не найдена");
        }
        return folder;
    }


    public Folder getFolderById(int id, Connection connection) throws FolderDAOException {
        Folder folder = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_FOLDER_BY_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            connection.setAutoCommit(false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                folder = initializeFolder(resultSet, connection);
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения папки", e1);
            throw new FolderDAOException("Ошибка получения папки", e1);
        }

        if(folder == null){
            DAO_LOGGER.info("Папка с ID " + id + " не найдена");
            throw new FolderDAOException("Папка с ID " + id + " не найдена");
        }
        return folder;
    }

    public Boolean userFolderExists(User user, String name) throws FolderDAOException {
        Folder folder = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FOLDER_BY_NAME_AND_USER_SQL_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, user.getId_User());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения папки", e1);
            throw new FolderDAOException("Ошибка получения папки", e1);
        }
        return false;
    }
    public Boolean departmentFolderExists(int departmentID, String name) throws FolderDAOException {
        Folder folder = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENT_FOLDER_BY_NAME_SQL_QUERY)) {
            preparedStatement.setInt(1, departmentID);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка соединения", e1);
            throw new FolderDAOException("Ошибка получения папки", e1);
        }
        return false;
    }

    public List<Folder> getFoldersByDepartmentID(int departmentID) throws FolderDAOException {
        List<Folder> folderList = new ArrayList<>();
        Folder folder = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENTTOFOLDERS_BY_DEPARTMENT_ID_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, departmentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                try{
                    folder = getFolderById(resultSet.getInt(ID_FOLDER_COLUMN), connection);
                    if(!folder.isFlagDeleted() && !folder.isFlagErased()){
                        folderList.add(folder);
                    }
                } catch (FolderDAOException e) {
                    DAO_LOGGER.error("Ошибка получения папки по id = "+resultSet.getInt(ID_FOLDER_COLUMN), e);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка получения cписка папок отдела по id = "+departmentID, e);
        }
        return folderList;
    }


    public List<Folder> getStorageFoldersByUserID(long userID) throws FolderDAOException {
        List<Folder> folderList = new ArrayList<>();
        Folder folder = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERTOFOLDERS_BY_USER_ID_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                try{
                    folder = getFolderById(resultSet.getInt(ID_FOLDER_COLUMN), connection);
                    if(folder.isFlagHidden() && folder.getUserCreated().getId_User() != userID){
                        continue;
                    }
                    if (folder.isFlagDeleted() || folder.isFlagErased())
                    {
                        continue;
                    }
                    folderList.add(folder);
                } catch (FolderDAOException e) {
                    DAO_LOGGER.error("Ошибка получения папки по id = "+resultSet.getInt(ID_FOLDER_COLUMN), e);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка получения cписка папок пользователя по id = "+userID, e);
        }
        return folderList;
    }


    public List<Folder> getSharedDocumentsByUserID(long userID) throws FolderDAOException {
        List<Folder> folderList = new ArrayList<>();
        List<Folder> folderListFromFilesAccess = new ArrayList<>();
        Folder folder = null;

        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERFOLDERS_BY_USER_ID_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            folderListFromFilesAccess = getSharedFoldersFromFilesAccessByUserID(userID);
            while (resultSet.next()) {
                try{
                    folder = getFolderById(resultSet.getInt(ID_FOLDER_COLUMN), connection);
                    if(!folder.isFlagDeleted() && !folder.isFlagErased()){
                        folderList.add(folder);
                    }
                } catch (FolderDAOException e) {
                    DAO_LOGGER.error("Ошибка получения папки по id = "+resultSet.getInt(ID_FOLDER_COLUMN), e);
                }
            }
            connection.commit();
            for(Folder tempFolder : folderListFromFilesAccess){
                if(!folderList.stream().filter(x->x.getId_Folder() == tempFolder.getId_Folder()).findAny().isPresent()){
                    folderList.add(tempFolder);
                }
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка получения cписка папок отдела по id = "+userID, e);
        }
        return folderList;
    }
    public List<Folder> getSharedFoldersFromFilesAccessByUserID(long userID) throws FolderDAOException {
        List<Folder> folderList = new ArrayList<>();
        Folder folder = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERFILES_BY_USER_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                try{
                    folder = getFolderById(resultSet.getInt(ID_FOLDER_COLUMN), connection);
                    if(!folder.isFlagDeleted()){
                        folderList.add(folder);
                    }
                } catch (FolderDAOException e) {
                    DAO_LOGGER.error("Ошибка получения папки по id = "+resultSet.getInt(ID_FOLDER_COLUMN), e);
                }
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка получения cписка папок отдела по id = "+userID, e);
        }
        return folderList;
    }


    public List<Folder> getDeletedFoldersByUser(User user) throws FolderDAOException {
        List<Folder> folderList = new ArrayList<>();
        List<Folder> folderListFromDeletedFiles = new ArrayList<>();
        Folder folder = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FOLDERS_DELETED_BY_USER_SQL_QUERY)) {
            connection.setAutoCommit(false);
            ResultSet resultSet = preparedStatement.executeQuery();
            folderListFromDeletedFiles = getFoldersFromDeletedFilesByUser(user);

            while (resultSet.next()) {
                try{
                    folder = getFolderById(resultSet.getInt(ID_FOLDER_COLUMN), connection);
                    if (user.getId_Role() == 2 || user.getId_Role() == 3){
                        folderList.add(folder);
                        continue;
                    }
                    if(user.getId_User() == folder.getUserDeleted().getId_User()){
                        folderList.add(folder);
                        continue;
                    }
                    if(user.getId_User() == folder.getUserCreated().getId_User()){
                        folderList.add(folder);
                    }
                } catch (FolderDAOException e) {
                    DAO_LOGGER.error("Ошибка получения папки по id = "+resultSet.getInt(ID_FOLDER_COLUMN), e);
                }
            }
             ;
            for(Folder tempFolder : folderListFromDeletedFiles){
                if(!folderList.stream().filter(x->x.getId_Folder() == tempFolder.getId_Folder()).findAny().isPresent()){
                    folderList.add(tempFolder);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка получения папки по id = "+user.getId_User(), e);
        }
        return folderList;
    }

    public List<Folder> getFoldersFromDeletedFilesByUser(User user) throws FolderDAOException {
        List<Folder> folderListFromDeletedFiles = new ArrayList<>();
        Folder folder = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FOLDERS_FROM_DELETED_FILES_BY_USER_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, user.getId_User());
            preparedStatement.setLong(2, user.getId_User());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                try{
                    folder = getFolderById(resultSet.getInt(ID_FOLDER_COLUMN), connection);
                    folderListFromDeletedFiles.add(folder);
                } catch (FolderDAOException e) {
                    DAO_LOGGER.error("Ошибка получения папки по id = "+resultSet.getInt(ID_FOLDER_COLUMN), e);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка получения папки по id = "+user.getId_User(), e);
        }
        return folderListFromDeletedFiles;
    }

    private Folder initializeFolder(ResultSet resultSet, Connection connection) throws SQLException{
        Folder folder = new Folder();
        folder.setId_Folder(resultSet.getInt(ID_FOLDER_COLUMN));
        folder.setName(resultSet.getString(NAME_COLUMN));
        folder.setFlagHidden(resultSet.getBoolean(FLAG_HIDDEN_COLUMN));
        folder.setFlagDeleted(resultSet.getBoolean(FLAG_DELETED_COLUMN));
        folder.setFlagErased(resultSet.getBoolean(FLAG_ERASED_COLUMN));
        long userCreatedId = resultSet.getInt(ID_USERCREATED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                folder.setUserCreated(new UserDAO().getUserById(userCreatedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        long userDeletedId = resultSet.getInt(ID_USERDELETED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                folder.setUserDeleted(new UserDAO().getUserById(userDeletedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        long userErasedId = resultSet.getLong(ID_USERERASED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                folder.setUserErased(new UserDAO().getUserById(userErasedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        folder.setCreationDate(resultSet.getTimestamp(CREATIONDATE_COLUMN));
        return folder;
    }


    public void addDepartmentFolder(String folderName, int ID_Department, boolean flagHidden, User user) throws FolderDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FOLDER_SQL_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, folderName);
            preparedStatement.setBoolean(2, flagHidden);
            preparedStatement.setLong(3, user.getId_User());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                int last_inserted_id = rs.getInt(1);
                addDepartmentToFolder(ID_Department, last_inserted_id);
            }
            connection.commit();
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка добавления папки", e1);
            throw new FolderDAOException("Ошибка добавления папки", e1);
        }
    }
    public void addDepartmentToFolder(int id_department, int id_folder) throws FolderDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_DEPARTMENTTOFOLDER_SQL_QUERY)) {
            preparedStatement.setInt(1, id_department);
            preparedStatement.setInt(2, id_folder);
            preparedStatement.executeUpdate();
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка добавления записи в таблицу папок отдела", e1);
            throw new FolderDAOException("Ошибка добавления записи в таблицу папок отдела", e1);
        }
    }
    public void addStorageFolder(String folderName, boolean flagHidden, User user) throws FolderDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FOLDER_SQL_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, folderName);
            preparedStatement.setBoolean(2, flagHidden);
            preparedStatement.setLong(3, user.getId_User());
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                int last_inserted_id = rs.getInt(1);
                addUserToFolder(user.getId_User(), last_inserted_id);
            }
            connection.commit();
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка добавления папки", e1);
            throw new FolderDAOException("Ошибка добавления папки", e1);
        }
    }
    public void addUserToFolder(long id_user, int id_folder) throws FolderDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERTOFOLDER_SQL_QUERY)) {
            preparedStatement.setLong(1, id_user);
            preparedStatement.setInt(2, id_folder);
            preparedStatement.executeUpdate();
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка добавления записи в таблицу папок пользователя", e1);
            throw new FolderDAOException("Ошибка добавления записи в таблицу папок пользователя", e1);
        }
    }



    public void updateFolder(Folder folder) throws FolderDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FOLDER_SQL_QUERY)) {
            preparedStatement.setString(1, folder.getName());
            System.out.println("updateFolder: "+folder.isFlagHidden());
            preparedStatement.setBoolean(2, folder.isFlagHidden());
            preparedStatement.setBoolean(3, folder.isFlagDeleted());
            if (folder.getUserDeleted() == null){
                preparedStatement.setNull(4, Types.BIGINT);
            }
            else {
                preparedStatement.setLong(4, folder.getUserDeleted().getId_User());
            }
            preparedStatement.setInt(5, folder.getId_Folder());
            preparedStatement.executeUpdate();
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка редактирования папки", e1);
            throw new FolderDAOException("Ошибка редактирования папки", e1);
        }
    }
    public void eraseFolder(Folder folder) throws FolderDAOException, FileDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ERASE_FOLDER_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setBoolean(1, folder.isFlagErased());
            if (folder.getUserErased() == null){
                preparedStatement.setNull(2, Types.BIGINT);
            }
            else {
                preparedStatement.setLong(2, folder.getUserErased().getId_User());
            }
            preparedStatement.setLong(3, folder.getId_Folder());
            preparedStatement.executeUpdate();
            new CommonDAO().updateFilesFlagErasedByFolderID(folder, connection);
            connection.commit();

        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка удаления папки с корзины", e1);
            throw new FolderDAOException("Ошибка удаления папки с корзины", e1);
        } catch (FileDAOException e) {
            DAO_LOGGER.error("Ошибка удаления файлов папки с корзины", e);
            throw new FileDAOException("Ошибка удаления файлов папки с корзины", e);
        }
    }
    public void updateFolderFlagDeleted(Folder folder) throws FolderDAOException, FileDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FOLDER_FLAGDELETED_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setBoolean(1, folder.isFlagDeleted());
            if (folder.getUserDeleted() == null){
                preparedStatement.setNull(2, Types.BIGINT);
            }
            else {
                preparedStatement.setLong(2, folder.getUserDeleted().getId_User());
            }
            preparedStatement.setLong(3, folder.getId_Folder());
            preparedStatement.executeUpdate();
            new CommonDAO().updateFilesFlagDeletedByFolderID(folder, connection);
            connection.commit();
        }  catch (SQLException e1){
            if(folder.isFlagDeleted()){
                DAO_LOGGER.error("Ошибка удаления папки", e1);
                throw new FolderDAOException("Ошибка удаления папки", e1);
            } else{
                DAO_LOGGER.error("Ошибка восстановления папки", e1);
                throw new FolderDAOException("Ошибка восстановления папки", e1);
            }
        } catch (FileDAOException e) {
            if(folder.isFlagDeleted()){
                DAO_LOGGER.error("Ошибка удаления файлов папки", e);
                throw new FolderDAOException("Ошибка удаления файлов папки", e);
            } else{
                DAO_LOGGER.error("Ошибка восстановления файлов папки", e);
                throw new FolderDAOException("Ошибка восстановления файлов папки", e);
            }
        }
    }
}
