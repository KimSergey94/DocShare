package kz.itbc.docshare.database.DAO;

import kz.itbc.docshare.database.ConnectionPoolDBCP;
import kz.itbc.docshare.entity.Position;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PositionDAO {
    private final BasicDataSource CONNECTION = ConnectionPoolDBCP.getInstance();

    public Set<Position> getAllPositions() throws SQLException{
        final String sql = "SELECT * FROM UR_UserPositions";
        Set<Position> positions = new HashSet<>();
        Position position;

        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                position = initializePosition(resultSet);
                if (!position.isDeleted()) {
                    positions.add(position);
                }
            }
        }
        return positions;
    }

    public List<Position> getPositionsByKeyword(String keyword) throws SQLException{
        final String sql = "SELECT * FROM UR_UserPositions WHERE LocaleRU LIKE ? OR LocaleKZ LIKE ?";
        keyword = keyword + "%";
        List<Position> positions = new ArrayList<>();
        Position position;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                position = initializePosition(resultSet);
                if (!position.isDeleted()) {
                    positions.add(position);
                }
            }
        }
        return positions;
    }

    public Position getPositionById(int id) throws SQLException {
        final String sql = "SELECT * FROM UR_UserPositions where ID_UserPosition = ?";
        Position position = null;
        try (Connection connection = CONNECTION.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                position = initializePosition(resultSet);
            }
        }
        return position;
    }

    private Position initializePosition(ResultSet resultSet) throws SQLException {
        Position position = new Position();
        position.setId(resultSet.getInt("ID_UserPosition"));
        position.setDeleted(resultSet.getBoolean("FlagDeleted"));
        position.setLocaleRU(resultSet.getString("LocaleRU"));
        position.setLocaleKZ(resultSet.getString("LocaleKZ"));
        int parentPositionId = resultSet.getInt("ID_ParentUserPosition");
        if (!resultSet.wasNull()) {
            position.setParentPosition(getPositionById(parentPositionId));
        } else {
            position.setParentPosition(null);
        }
        return position;
    }
}
