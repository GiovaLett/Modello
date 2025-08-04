package org.example.connessioneDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB {

    private static Connection conn=null;

    private static final String URL ="jdbc:postgresql://localhost:5432/Hackathon";
    private static final String USER ="postgres";
    private static final String PASSWORD ="0000";


    public static Connection getInstance() throws SQLException {  //Questo è il Singleton

        if(conn==null || conn.isClosed()){
            conn= DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conn;

    }
}
