package kz.itbc.docshare.database.DAO;

import kz.itbc.docshare.database.ConnectionPoolDBCP;
import kz.itbc.docshare.entity.File;
import kz.itbc.docshare.entity.Folder;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

import static kz.itbc.docshare.constants.SQLConstant.*;

public class CommonDAO {
    private static final Logger DAO_LOGGER = LogManager.getRootLogger();
    private static final BasicDataSource CONNECTION = ConnectionPoolDBCP.getInstance();

    public void addFileAccess(long id_file, List<User> users) throws FileAccessDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SHARE_FILE_WITH_USERS_SQL_QUERY)) {
            connection.setAutoCommit(false);
            for (User user : users) {
                preparedStatement.setLong(1, user.getId_User());
                preparedStatement.setLong(2, id_file);
                preparedStatement.setLong(3, user.getId_User());
                preparedStatement.setLong(4, id_file);
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка при работе с соединением при добавлении записи в таблицу доступа к файлам", e);
            throw new FileAccessDAOException("Ошибка при работе с соединением при добавлении записи в таблицу доступа к файлам", e);
        }
    }


    public void addFolderAccess(int id_folder, List<User> users) throws FolderAccessDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SHARE_FOLDER_WITH_USERS_SQL_QUERY)) {
            connection.setAutoCommit(false);
            for (User user : users) {
                preparedStatement.setLong(1, user.getId_User());
                preparedStatement.setLong(2, id_folder);
                preparedStatement.setLong(3, user.getId_User());
                preparedStatement.setLong(4, id_folder);
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            DAO_LOGGER.error("Ошибка при работе с соединением при добавлении записи в таблицу доступа к файлам по папке", e);
            throw new FolderAccessDAOException("Ошибка при работе с соединением при добавлении записи в таблицу доступа к файлам по папке", e);
        }
    }

    public boolean isFolderSharedOrUploadedByUser(User user, Folder folder) throws CommonDAOException {
        try (Connection connection = CONNECTION.getConnection()) {
            if(isFolderSharedByUser(user, folder, connection) &&
               isFolderUploadedByUser(user, folder, connection)) {
                return true;
            } else {
                return false;
            }
        } catch (FolderAccessDAOException e) {
            DAO_LOGGER.error("Ошибка получения записи в таблице доступа к папкам", e);
        } catch (UserToFolderDAOException e) {
            DAO_LOGGER.error("Ошибка получения записи в таблице папок загруженных пользователем", e);
        } catch (SQLException e) {
            throw new CommonDAOException("Ошибка проверки пользователя на доступ к папке", e);
        }
        return false;
    }

    public boolean isFolderSharedByUser(User user, Folder folder, Connection connection) throws FolderAccessDAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_RECORD_IN_FOLDERS_ACCESS_SQL_QUERY)) {
            preparedStatement.setLong(1, user.getId_User());
            preparedStatement.setInt(2, folder.getId_Folder());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения записи в таблице доступа к папкам", e1);
            throw new FolderAccessDAOException("Ошибка получения записи в таблице доступа к папкам", e1);
        }
        return false;
    }
    public boolean isFolderUploadedByUser(User user, Folder folder, Connection connection) throws UserToFolderDAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_RECORD_IN_USERTOFOLDERS_SQL_QUERY)) {
            preparedStatement.setLong(1, user.getId_User());
            preparedStatement.setInt(2, folder.getId_Folder());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения записи в таблице папок загруженных пользователем", e1);
            throw new UserToFolderDAOException("Ошибка получения записи в таблице папок загруженных пользователем", e1);
        }
        return false;
    }

    public boolean isFileSharedWithUser(User user, File file) throws CommonDAOException {
        try (Connection connection = CONNECTION.getConnection()) {
            if(isFileSharedByUser(user, file)) {
                return true;
            } else {
                return false;
            }
        } catch (FileAccessDAOException e) {
            DAO_LOGGER.error("Ошибка получения записи в таблице доступа к папкам", e);
        } catch (SQLException e) {
            throw new CommonDAOException("Ошибка проверки пользователя на доступ к папке", e);
        }
        return false;
    }
    public boolean isFileSharedByUser(User user, File file) throws FileAccessDAOException {
        try (Connection connection = CONNECTION.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CHECK_RECORD_IN_FILES_ACCESS_SQL_QUERY)) {
            preparedStatement.setLong(1, user.getId_User());
            preparedStatement.setLong(2, file.getId_File());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения записи в таблице доступа к файлам", e1);
            throw new FileAccessDAOException("Ошибка получения записи в таблице доступа к файлам", e1);
        }
        return false;
    }
    public boolean isFileUploadedByUser(User user, File file) throws FileDAOException {
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_RECORD_IN_FILES_SQL_QUERY)) {
            preparedStatement.setLong(1, user.getId_User());
            preparedStatement.setLong(2, file.getId_File());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        }  catch (SQLException e1){
            DAO_LOGGER.error("Ошибка получения записи в таблице файлов", e1);
            throw new FileDAOException("Ошибка получения записи в таблице файлов", e1);
        }
        return false;
    }

    public void updateFilesFlagDeletedByFolderID(Folder folder, Connection connection) throws FileDAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILES_FLAGDELETED_BY_FOLDERID_SQL_QUERY)) {
            preparedStatement.setBoolean(1, folder.isFlagDeleted());
            if (folder.getUserDeleted() == null){
                preparedStatement.setNull(2, Types.BIGINT);
            }
            else {
                preparedStatement.setLong(2, folder.getUserDeleted().getId_User());
            }
            preparedStatement.setInt(3, folder.getId_Folder());
            preparedStatement.executeUpdate();
        } catch (SQLException e1){
            if(folder.isFlagDeleted()){
                DAO_LOGGER.error("Ошибка удаления файлов по папке", e1);
                throw new FileDAOException("Ошибка удаления файлов по папке", e1);
            } else {
                DAO_LOGGER.error("Ошибка восстановления файлов по папке", e1);
                throw new FileDAOException("Ошибка восстановления файлов по папке", e1);
            }

        }
    }
    public void updateFilesFlagErasedByFolderID(Folder folder, Connection connection) throws FileDAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILES_FLAGERASED_BY_FOLDERID_SQL_QUERY)) {
            preparedStatement.setBoolean(1, folder.isFlagErased());
            if (folder.getUserErased() == null){
                preparedStatement.setNull(2, Types.BIGINT);
            }
            else {
                preparedStatement.setLong(2, folder.getUserErased().getId_User());
            }
            preparedStatement.setInt(3, folder.getId_Folder());
            preparedStatement.executeUpdate();
        } catch (SQLException e1){
            DAO_LOGGER.error("Ошибка удаления файлов с корзины по папке", e1);
            throw new FileDAOException("Ошибка удаления файлов с корзины по папке", e1);
        }
    }


}
