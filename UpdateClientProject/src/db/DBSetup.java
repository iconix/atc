package db;

public class DBSetup {
    
    public DBSetup() {
        //do nothing
    }
    /**
     * Reset all the relevant table in the database
     * @param arg0 
     */
    public static void main(String[] arg0) {
        DBResetClientAccountTable.ResetClientAccountTable();
        DBResetGpsCoordinateTable.ResetGpsCoordinateTable();
        DBResetPinLocationTable.ResetPinLocationTable();
    }
	
}
