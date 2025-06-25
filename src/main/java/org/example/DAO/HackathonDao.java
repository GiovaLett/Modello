package org.example.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HackathonDao {

    void addUtenteRegistratoDB(String id,String nome, String cognome, String email, String password) throws SQLException;

     int[] getCostantiID() throws SQLException;
    void getAllUtentiRegistratiDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<String> cognome, ArrayList<String> email,
                                  ArrayList<String> password, ArrayList<String> idHackathon, ArrayList<String> idTeam) throws SQLException;


   void  addHackathonDB(String id,String nome) throws SQLException;
   void removeHackathonDB(String id) throws SQLException;

   void getAllHackathonDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<Integer> n_max_partec, ArrayList<Integer> n_team, ArrayList<String> problema,
                          ArrayList<Boolean> team_suff, ArrayList<Boolean>view_problema, ArrayList<Boolean> evento_finito, ArrayList<Boolean> votaz_conclusa) throws SQLException;




}

