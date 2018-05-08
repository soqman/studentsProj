package ru.innopolis.vasiliev.studentsproj.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerJDBCImpl implements ConnectionManager {
    private static ConnectionManager connectionManager;

    private static final String DRIVERCLASS = "org.postgresql.Driver";
    private static final String PSTGRURS = "jdbc:postgresql://localhost:5432/studentsproj_vvn";
    private static final String PSTGRUSER = "postgres";
    private static final String PSTGRUSERPASSWORD = "sa";

    public static ConnectionManager getInstance(){
        if(connectionManager==null){
            connectionManager=new ConnectionManagerJDBCImpl();
        }
        return connectionManager;
    }

    private ConnectionManagerJDBCImpl(){

    }

    @Override
    public Connection getConnection() {
        Connection connection=null;
        try {
            Class.forName(DRIVERCLASS);
            connection = DriverManager.getConnection(
                    PSTGRURS,
                    PSTGRUSER,
                    PSTGRUSERPASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
