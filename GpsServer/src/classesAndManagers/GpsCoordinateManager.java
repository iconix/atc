package classesAndManagers;

import java.sql.*;
import staticVariables.*;

public class GpsCoordinateManager {


    public GpsCoordinateManager() {
        //do nothing
    }

    /**
     * Add a new coordinate to the DB table with the information from the GpsCoordinate instance
     * @param connection to the DB
     * @param coordinate instance of the GpsCoordinate
     */
    public void addNewGpsCoordinate(Connection connection, GpsCoordinate coordinate) {

        String query = "INSERT INTO " + TableName.coordinateDB + 
                            " VALUES(\"" + coordinate.accountID + "\", \"" +
                            coordinate.deviceID + "\", " +
                            coordinate.time + ", " +
                            coordinate.longitude + ", " +
                            coordinate.latitude + ")";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the coordinate in the GpsCoordinate in the string format separate by the given delimiter
     * @param instance of GpsCoordinate
     * @return string format of the coordinate
     */
    public String getGpsCoordinateInStringFormat(GpsCoordinate coordinate) {
        return coordinate.accountID + SpecialCharacters.delimiter + coordinate.deviceID + SpecialCharacters.delimiter +
                    coordinate.time + SpecialCharacters.delimiter + coordinate.longitude + SpecialCharacters.delimiter + 
                    coordinate.latitude + SpecialCharacters.endLn;
    }
}
