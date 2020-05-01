package dma.location;

import dma.database.DatabaseConnector;
import dma.database.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository implements Repository<Location> {

    private DatabaseConnector connector;

    public LocationRepository() {
        this.connector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        ResultSet result = connector.fetchData("SELECT * FROM location");

        if (result == null) {
            System.out.println("No Locations found!");
            return null;
        }

        try {
            while (result.next()) {
                Location tempLocation = new Location();
                tempLocation.setPlz(result.getInt("plz"));
                tempLocation.setName(result.getString("name"));
                tempLocation.setPrice(result.getInt("price"));
                locations.add(tempLocation);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connector.closeConnection();
            return locations;
        }
    }

    @Override
    public Location findOne(int plz) {
        return null;
    }

    @Override
    public int create(Location entity) {
        return 0;
    }
}
