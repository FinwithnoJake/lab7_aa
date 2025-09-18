package server.repo;

import common.model.*;
import server.exceptions.UserNotOwnerOfObject;
import server.service.CityService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static server.repo.DbUtils.*;


public class CityJDBCBasedRepository {
    public final Connection connection;
    private static final String TABLE_CITY = " city ";

    public CityJDBCBasedRepository(Connection connection) {
        this.connection = connection;
    }

    public int add(User user, City city) throws SQLException {
        int id = user.getId();
        String query = INSERT + TABLE_CITY
                + "(name, x, y, creation_date, area, population, metersAboveSeaLevel, carCode, agglomeration, government, human, owner_id)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, city.getName());
        statement.setInt(2, city.getCoordinates().getX());
        statement.setLong(3, city.getCoordinates().getY());
        statement.setDate(4, city.getSqlCreationDate());
        statement.setFloat(5, city.getArea());
        statement.setLong(6, city.getPopulation());
        statement.setFloat(7, city.getMetersAboveSeaLevel());
        statement.setLong(8, city.getCarCode());
        statement.setLong(9, city.getAgglomeration());
        statement.setObject(10, city.getGovernment(), Types.OTHER);
        statement.setString(11, city.getHuman());
        statement.setInt(12, id);

        statement.execute();
        var rs = statement.getResultSet();
        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public int updateCity(City city) throws SQLException, UserNotOwnerOfObject {
        String set = """
                name = ?,
                x = ?,
                y = ?,
                creation_date = ?,
                area = ?,
                population = ?,
                metersAboveSeaLevel = ?,
                carCode = ?
                agglomeration = ?
                government = ?
                human = ?
                """;
        PreparedStatement statement = connection.prepareStatement(UPDATE + TABLE_CITY + " SET "
                + set + WHERE + " id = ?");
        statement.setString(1, city.getName());
        statement.setInt(2, city.getCoordinates().getX());
        statement.setLong(3, city.getCoordinates().getY());

        statement.setDate(4, city.getSqlCreationDate());

        statement.setFloat(5, city.getArea());
        statement.setLong(6, city.getPopulation());
        statement.setFloat(7, city.getMetersAboveSeaLevel());
        statement.setLong(8, city.getCarCode());
        statement.setLong(9, city.getAgglomeration());
        statement.setObject(10, city.getGovernment(), Types.OTHER);
        statement.setString(11, city.getHuman());
        statement.setInt(12, city.getId());

        return statement.executeUpdate();

    }

    public City getCIty(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                (SELECT + "name, x, y, creation_date, area, population, metersAboveSeaLevel, carCode, agglomeration, government, human, owner_id, creation_date"
                        + FROM + TABLE_CITY + " as c " + WHERE + "? = c.id;");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        City cityFromResultSet = getCityFromResultSet(resultSet, false);
        cityFromResultSet.setId(id);

        return cityFromResultSet;
    }

    public Collection<City> readCollection() throws SQLException {
        String query = SELECT + "id, name, x, y, creation_date, area, population, metersAboveSeaLevel, carCode, agglomeration, government, human, owner_id, creation_date "
                + FROM + TABLE_CITY + ";";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<City> lst = new ArrayList<>();
        while (resultSet.next()) {
            City c = getCityFromResultSet(resultSet, true);
            lst.add(c);
        }

        return lst;
    }

    public int clear(int userId) throws SQLException {
        String query = DELETE + TABLE_CITY + WHERE + "owner_id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);

        return statement.executeUpdate();
    }

    public int remove(int userId, int cityId) throws SQLException {
        String query = DELETE + TABLE_CITY + WHERE + "owner_id = ? AND id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, cityId);

        return statement.executeUpdate();
    }

    private City getCityFromResultSet(ResultSet rs, boolean withId) throws SQLException {
        int i = 1;

        City city = new City();
        if (withId) {
            city.setId(rs.getInt(i++));
        }
        city
                .setName(rs.getString(i++))
                .setCoordinates(new Coordinates(rs.getInt(i++), rs.getLong(i++)))
                .setCreationDate(rs.getDate(i++).toLocalDate())
                .setArea(rs.getFloat(i++))
                .setPopulation(rs.getLong(i++))
                .setMetersAboveSeaLevel(rs.getFloat(i++))
                .setCarCode(rs.getLong(i++))
                .setAgglomeration(rs.getLong(i++))
                .setGovernment(Government.valueOf((rs.getString(i++).toString())))
                .setHuman(rs.getString(i++))
                .setOwnerId(rs.getInt(i++))
                .setCreationDate(rs.getDate(i++).toLocalDate());

        return city;
    }

    private boolean isUserCreatorOfCity(int userId, int cityId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
                (SELECT + " COUNT(*) " + FROM + TABLE_CITY + " as c "
                        + WHERE + "? = c.id AND " + "? = c.owner_id;");
        statement.setInt(1, cityId);
        statement.setInt(2, userId);
        ResultSet resultSet = statement.executeQuery();
        int count = resultSet.getInt(1);

        return count == 1;
    }


}
