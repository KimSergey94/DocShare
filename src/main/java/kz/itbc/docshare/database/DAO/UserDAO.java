package kz.itbc.docshare.database.DAO;

import kz.itbc.docshare.database.ConnectionPoolDBCP;
import kz.itbc.docshare.entity.User;
import kz.itbc.docshare.exception.DepartmentDAOException;
import kz.itbc.docshare.exception.UserDAOException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.itbc.docshare.constants.SQLConstant.*;

public class UserDAO {
    private static final BasicDataSource CONNECTION = ConnectionPoolDBCP.getInstance();
    private static final Logger DAO_LOGGER = LogManager.getRootLogger();
    private static final Logger SERVICE_LOGGER = LogManager.getRootLogger();



    public List<User> getUsers() throws UserDAOException{
        List<User> users = new ArrayList<>();
        User user = new User();
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_SQL_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = initializeUser(resultSet, connection);
                users.add(user);
            }
        } catch (SQLException e){
            DAO_LOGGER.error("Ошибка получения пользователя с ID ", e);
            throw new UserDAOException("Ошибка получения пользователя с ID ", e);
        }
        return users;
    }

    public List<User> getUsersByKeyword(String keyword) throws UserDAOException{
        List<User> users = new ArrayList<>();
        User user = new User();
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_BY_KEYWORD_SQL_QUERY)) {
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = initializeUser(resultSet, connection);
                users.add(user);
            }
        } catch (SQLException e){
            DAO_LOGGER.error("Ошибка получения пользователей по ключевому слову", e);
            throw new UserDAOException("Ошибка получения пользователей по ключевому слову", e);
        }
        return users;
    }



    public User getUserByLogin(String login) throws UserDAOException {
        User user = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = initializeUser(resultSet, connection);
            }
            connection.commit();
        } catch (SQLException e){
            DAO_LOGGER.error("Ошибка получения пользователя с логином " + login, e);
            throw new UserDAOException("Ошибка получения пользователя с логином " + login, e);
        }

        if(user == null){
            DAO_LOGGER.info("Пользователь с логином " + login + " не найден");
            throw new UserDAOException("Пользователь с логином " + login + " не найден");
        }
        return user;
    }


    public User getUserById(long id) throws UserDAOException {
        User user = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = initializeUser(resultSet, connection);
            }
            connection.commit();
        } catch (SQLException e){
            DAO_LOGGER.error("Ошибка получения пользователя с ID " + id, e);
            throw new UserDAOException("Ошибка получения пользователя с ID " + id, e);
        }

        if(user == null){
            DAO_LOGGER.info("Пользователь с ID " + id + " не найден");
            throw new UserDAOException("Пользователь с ID " + id + " не найден");
        }
        return user;
    }

    public User getUserById(long id, Connection connection) throws UserDAOException {
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID_SQL_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = initializeUser(resultSet, connection);
            }
            connection.commit();
        } catch (SQLException e){
            DAO_LOGGER.error("Ошибка получения пользователя с ID " + id, e);
            throw new UserDAOException("Ошибка получения пользователя с ID " + id, e);
        }

        if(user == null){
            DAO_LOGGER.info("Пользователь с ID " + id + " не найден");
            throw new UserDAOException("Пользователь с ID " + id + " не найден");
        }
        return user;
    }

    private User initializeUser(ResultSet resultSet, Connection connection) throws SQLException, UserDAOException{
        User user = new User();
        try {
            RoleDAO roleDao = new RoleDAO();
            user.setId_User(resultSet.getLong(ID_USER_COLUMN));
            user.setLogin(resultSet.getString(LOGIN_COLUMN));
            user.setPassword(resultSet.getString(PASSWORD_COLUMN));
            user.setEmail(resultSet.getString(EMAIL_COLUMN));
            user.setPhone(resultSet.getString(PHONE_COLUMN));
            user.setFirstName(resultSet.getString(FIRST_NAME_COLUMN));
            user.setLastName(resultSet.getString(LAST_NAME_COLUMN));
            user.setMiddleName(resultSet.getString(MIDDLE_NAME_COLUMN));
            user.setFlagDeleted(resultSet.getBoolean(FLAG_DELETED_COLUMN));
            user.setFlagAccess(resultSet.getBoolean(FLAG_ACCESS_COLUMN));
            user.setAccessIP(resultSet.getString(ACCESS_IP_COLUMN));
            user.setSessionID(resultSet.getString(SESSION_ID_COLUMN));
            user.setIin(resultSet.getString(IIN_COLUMN));
            user.setLastLogin(resultSet.getTimestamp(LAST_LOGIN_COLUMN));
            user.setLastLoginIP(resultSet.getString(LAST_LOGIN_IP_COLUMN));
            user.setClientName(resultSet.getString(CLIENT_NAME_COLUMN));
            user.setLastClientLogin(resultSet.getTimestamp(LAST_CLIENT_LOGIN_COLUMN));

            int userDepartmentId = resultSet.getInt("ID_Department");
            if (!resultSet.wasNull()) {
                DepartmentDAO departmentDAO = new DepartmentDAO();
                user.setDepartment(departmentDAO.getDepartmentById(userDepartmentId));
            } else {
                user.setDepartment(null);
            }

       /*     int userPositionId = resultSet.getInt("ID_UserPosition");
            if (!resultSet.wasNull()) {
                PositionDAO positionDAO = new PositionDAO();
                user.setPosition(positionDAO.getPositionById(userPositionId));
            } else {
                user.setPosition(null);
            }*/

            user.setId_Role(resultSet.getInt(ID_ROLE_COLUMN));
            user.setBlockedDate(resultSet.getTimestamp(BLOCKED_DATE_COLUMN));
            user.setFlagFirstLogin(resultSet.getBoolean(FLAG_FIRST_LOGIN_COLUMN));
            //user.setRole(roleDao.getRoleByID(resultSet.getInt(ID_ROLE_COLUMN), connection));  /// No SR_Roles table
        } catch (DepartmentDAOException e) {
            DAO_LOGGER.error("Ошибка получения отдела", e);
        }
        return user;
    }
}
