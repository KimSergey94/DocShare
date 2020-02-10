package kz.itbc.docshare.database.DAO;

import kz.itbc.docshare.database.ConnectionPoolDBCP;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.FileDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static kz.itbc.docshare.constants.SQLConstant.*;

public class FileDAO {
    private static final BasicDataSource CONNECTION = ConnectionPoolDBCP.getInstance();
    private static final Logger DAO_LOGGER = LogManager.getRootLogger();

    public File getFileById(long id) throws FileDAOException {
        File file = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FILE_BY_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                file = initializeFile(resultSet, connection);
            }
        } catch (SQLException ex) {
            DAO_LOGGER.error("Ошибка получения файла", ex);
            throw new FileDAOException("Ошибка получения файла", ex);
        }

        if (file == null) {
            DAO_LOGGER.info("Файл с ID " + id + " не найдена");
            throw new FileDAOException("Файл с ID " + id + " не найдена");
        }
        return file;
    }
    public File getFileByIdWithoutData(long id) throws FileDAOException {
        File file = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FILE_BY_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                file = initializeFileWithoutData(resultSet, connection);
            }
        } catch (SQLException ex) {
            DAO_LOGGER.error("Ошибка получения файла", ex);
            throw new FileDAOException("Ошибка получения файла", ex);
        }

        if (file == null) {
            DAO_LOGGER.info("Файл с ID " + id + " не найдена");
            throw new FileDAOException("Файл с ID " + id + " не найдена");
        }
        return file;
    }

    public ArrayList<File> getFilesByFolderId(int id) throws FileDAOException {
        ArrayList<File> filesList = new ArrayList<>();
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_FILES_BY_FOLDER_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                File file = initializeFile(resultSet, connection);
                filesList.add(file);
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка получения файлов по папке отдела с id = " + id, e);
            throw new FileDAOException("Ошибка получения файлов по папке отдела с id = " + id, e);
        }
        return filesList;
    }
    public ArrayList<File> getDeletedFilesByFolderID(User user, int id) throws FileDAOException {
        ArrayList<File> filesList = new ArrayList<>();
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DELETED_FILES_BY_FOLDER_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                File file = initializeFile(resultSet, connection);
                if (user.getId_Role() == 2 && user.getId_Role() == 3){
                    filesList.add(file);
                    continue;
                }
                if(file.getUserDeleted() != null){
                    if(file.getUserDeleted().getId_User() == user.getId_User()){
                        filesList.add(file);
                        continue;
                    }
                }
                if(file.getUserCreated() != null){
                    if(file.isFlagHidden() && file.getUserCreated().getId_User() == user.getId_User()){
                        filesList.add(file);
                        continue;
                    }else if(file.getUserCreated().getId_User() == user.getId_User()){
                        filesList.add(file);
                        continue;
                    }
                }
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка получения файлов по папке отдела с id = " + id, e);
            throw new FileDAOException("Ошибка получения файлов по папке отдела с id = " + id, e);
        }
        return filesList;
    }


    public List<File> getAllFilesByKeyword(String keyword) throws FileDAOException {
        File folder = null;
        List<File> files = new ArrayList<>();
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FILES_BY_KEYWORD_SQL_QUERY)) {
            preparedStatement.setString(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                folder = initializeFile(resultSet, connection);
                if(!folder.isFlagErased()){
                    files.add(folder);
                }
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения папок по ключевому слову", e1);
            throw new FileDAOException("Ошибка получения папок по ключевому слову", e1);
        }
        return files;
    }



    private File initializeFile(ResultSet resultSet, Connection connection) throws SQLException{
        File file = new File();
        file.setId_File(resultSet.getLong(ID_FILE_COLUMN));
        file.setName(resultSet.getString(NAME_COLUMN));
        byte[] bytes = resultSet.getBytes(DATA_COLUMN);
        if(bytes != null){
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            file.setData(in);
        }
        file.setFlagHidden(resultSet.getBoolean(FLAG_HIDDEN_COLUMN));
        file.setFlagDeleted(resultSet.getBoolean(FLAG_DELETED_COLUMN));
        file.setFlagErased(resultSet.getBoolean(FLAG_ERASED_COLUMN));
        file.setId_FileType(resultSet.getInt(ID_FILE_TYPE_COLUMN));
        file.setId_Folder(resultSet.getInt(ID_FOLDER_COLUMN));
        long userCreatedId = resultSet.getLong(ID_USERCREATED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                file.setUserCreated(new UserDAO().getUserById(userCreatedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        long userDeletedId = resultSet.getLong(ID_USERDELETED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                file.setUserDeleted(new UserDAO().getUserById(userDeletedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        long userErasedId = resultSet.getLong(ID_USERERASED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                file.setUserErased(new UserDAO().getUserById(userErasedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        file.setCreationDate(resultSet.getTimestamp(CREATIONDATE_COLUMN));
        return file;
    }
    private File initializeFileWithoutData(ResultSet resultSet, Connection connection) throws SQLException{
        File file = new File();
        file.setId_File(resultSet.getLong(ID_FILE_COLUMN));
        file.setName(resultSet.getString(NAME_COLUMN));
        file.setFlagHidden(resultSet.getBoolean(FLAG_HIDDEN_COLUMN));
        file.setFlagDeleted(resultSet.getBoolean(FLAG_DELETED_COLUMN));
        file.setFlagErased(resultSet.getBoolean(FLAG_ERASED_COLUMN));
        file.setId_FileType(resultSet.getInt(ID_FILE_TYPE_COLUMN));
        file.setId_Folder(resultSet.getInt(ID_FOLDER_COLUMN));
        long userCreatedId = resultSet.getLong(ID_USERCREATED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                file.setUserCreated(new UserDAO().getUserById(userCreatedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        long userDeletedId = resultSet.getLong(ID_USERDELETED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                file.setUserDeleted(new UserDAO().getUserById(userDeletedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        long userErasedId = resultSet.getLong(ID_USERERASED_COLUMN);
        if(!resultSet.wasNull()){
            try {
                file.setUserErased(new UserDAO().getUserById(userErasedId, connection));
            } catch (UserDAOException e) {
                DAO_LOGGER.error("Ошибка получения пользователя id = "+userCreatedId, e);
            }
        }
        file.setCreationDate(resultSet.getTimestamp(CREATIONDATE_COLUMN));
        return file;
    }

    public void updateFile(File file) throws FileDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILE_SQL_QUERY)) {
            preparedStatement.setString(1, file.getName());
            preparedStatement.setBoolean(2, file.isFlagHidden());
            preparedStatement.setBoolean(3, file.isFlagDeleted());
            if (file.getUserDeleted() == null){
                preparedStatement.setNull(4, Types.BIGINT);
            }
            else {
                preparedStatement.setLong(4, file.getUserDeleted().getId_User());
            }
            preparedStatement.setLong(5, file.getId_File());
            preparedStatement.executeUpdate();
        } catch (SQLException e1){
            DAO_LOGGER.error("Ошибка редактирования файла", e1);
            throw new FileDAOException("Ошибка редактирования файла", e1);
        }
    }
    public void eraseFile(File file) throws FileDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ERASE_FILE_SQL_QUERY)) {
            preparedStatement.setBoolean(1, file.isFlagErased());
            if (file.getUserDeleted() == null){
                preparedStatement.setNull(2, Types.BIGINT);
            }
            else {
                preparedStatement.setLong(2, file.getUserErased().getId_User());
            }
            preparedStatement.setLong(3, file.getId_File());
            preparedStatement.executeUpdate();
        } catch (SQLException e1){
            DAO_LOGGER.error("Ошибка удаления файла из корзины", e1);
            throw new FileDAOException("Ошибка удаления файла из корзины", e1);
        }
    }




    public void addFile(String fileName, byte[] data, boolean flagHidden, boolean flagDeleted, int ID_FileType,
                        int ID_Folder, User user) throws FileDAOException {
        System.out.println("tryingToAdd");
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FILE_SQL_QUERY)) {
            System.out.println("preparingStatements");

            preparedStatement.setString(1, fileName);
            preparedStatement.setBytes(2, data);
            preparedStatement.setBoolean(3, flagHidden);
            preparedStatement.setBoolean(4, flagDeleted);
            preparedStatement.setInt(5, ID_FileType);
            preparedStatement.setInt(6, ID_Folder);
            preparedStatement.setLong(7, user.getId_User());
            preparedStatement.setTimestamp(8, new Timestamp(new Date().getTime()));
            System.out.println("executingUpdate");

            preparedStatement.executeUpdate();
            System.out.println("updateIsExecuted");

        } catch (SQLException e1) {
            DAO_LOGGER.error("Ошибка добавления файла", e1);
            throw new FileDAOException("Ошибка добавления файла", e1);
        }
    }
}

