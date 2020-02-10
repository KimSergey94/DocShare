package kz.itbc.docshare.database.DAO;

import kz.itbc.docshare.database.ConnectionPoolDBCP;
import kz.itbc.docshare.entity.Department;
import kz.itbc.docshare.exception.DepartmentDAOException;
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

public class DepartmentDAO {
    private static final Logger DAO_LOGGER = LogManager.getRootLogger();
    private static final BasicDataSource CONNECTION = ConnectionPoolDBCP.getInstance();

    public Department getDepartmentById(int id) throws DepartmentDAOException {
        Department department = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENT_BY_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                department = initializeDepartment(resultSet);
            }
        } catch (SQLException e1) {
            DAO_LOGGER.error("Ошибка получения отдела", e1);
            throw new DepartmentDAOException("Ошибка получения отдела", e1);
        }
        return department;
    }

    public Department getDepartmentById(int id, Connection connection) throws DepartmentDAOException {
        Department department = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENT_BY_ID_SQL_QUERY)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                department = initializeDepartment(resultSet);
            }
        } catch (SQLException e1) {
            DAO_LOGGER.error("Ошибка получения отдела", e1);
            throw new DepartmentDAOException("Ошибка получения отдела", e1);
        }
        return department;
    }

    public List<Department> getDepartments() throws DepartmentDAOException {
        List<Department> departments = new ArrayList();
        Department department = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DEPARTMENTS_SQL_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                department = initializeDepartment(resultSet);
                departments.add(department);
            }
        } catch (SQLException e1) {
            DAO_LOGGER.error("Ошибка получения отдела", e1);
            throw new DepartmentDAOException("Ошибка получения отдела", e1);
        }
        return departments;
    }


    private Department initializeDepartment(ResultSet resultSet) throws SQLException{
        Department department = new Department();
        department.setId_Department(resultSet.getInt(ID_DEPARTMENT_COLUMN));
        department.setId_ParentDepartment(resultSet.getInt(ID_PARENT_DEPARTMENT_COLUMN));
        department.setPhone(resultSet.getString(PHONE_COLUMN));
        department.setLocaleRU(resultSet.getString(LOCALE_RU_COLUMN));
        department.setLocaleKZ(resultSet.getString(LOCALE_KZ_COLUMN));
        return department;
    }


}