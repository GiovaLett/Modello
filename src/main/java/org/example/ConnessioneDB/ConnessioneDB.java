package org.example.ConnessioneDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB {

    private static Connection conn=null;

    private static String url="jdbc:postgresql://localhost:5432/Hackathon";
    private static String user="postgres";
    private static String password="0000";


    public static Connection getInstance() throws SQLException {  //Questo è il Singleton

        if(conn==null || conn.isClosed()){
            conn= DriverManager.getConnection(url,user,password);
        }
        return conn;

    }
}
