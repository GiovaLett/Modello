package org.example.connessione_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDB {

    private static Connection conn=null;

    private static final String URL ="jdbc:postgresql://localhost:5432/Hackathon";
    private static final String USER ="postgres";
    private static final String PASSWORD ="0000";

    /**
     * Funzione che apre una connessione al DB assegnando quest'ultima all'attributo statico conn, questo nel caso in cui
     * esso sia NULL oppure presenta una connessione chiusa
     * @return un riferimento all attributo conn
     * @throws SQLException nel caso in cui il tentativo di connessione sollevi eccezioni
     */
    public static Connection getInstance() throws SQLException {  //Questo è il Singleton

        if(conn==null || conn.isClosed()){
            conn= DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conn;

    }
}
