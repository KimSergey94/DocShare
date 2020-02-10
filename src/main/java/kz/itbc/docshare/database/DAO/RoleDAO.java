package kz.itbc.docshare.database.DAO;

import kz.itbc.docshare.database.ConnectionPoolDBCP;
import kz.itbc.docshare.entity.Role;
import kz.itbc.docshare.exception.RoleDAOException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static kz.itbc.docshare.constants.SQLConstant.*;

public class RoleDAO {
    private static final BasicDataSource CONNECTION = ConnectionPoolDBCP.getInstance();
    private static final Logger DAO_LOGGER = LogManager.getRootLogger();

    public Role getRoleByID(int id, Connection connection) throws RoleDAOException {
        Role role = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_BY_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                role = initializeRole(resultSet);
            }
        } catch (SQLException e){
            DAO_LOGGER.error("Ошибка получения роли с ID " + id, e);
            throw new RoleDAOException("Ошибка получения роли с ID " + id, e);
        }

        if(role == null ){
            throw new RoleDAOException("Роль с ID " + id + " не найдена");
        }
        return role;
    }



    private Role initializeRole(ResultSet resultSet) throws SQLException{
        Role role = new Role();
        role.setId(resultSet.getInt(ID_ROLE_COLUMN));
        role.setDeleted(resultSet.getBoolean(FLAG_DELETED_COLUMN));
        role.setLocaleRU(resultSet.getString(LOCALE_RU_COLUMN));
        role.setLocaleKZ(resultSet.getString(LOCALE_KZ_COLUMN));
        return role;
    }

}