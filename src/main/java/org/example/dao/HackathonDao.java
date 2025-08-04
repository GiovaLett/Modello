package org.example.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HackathonDao {


    //UTENTI REGISTRATI
    void getAllUtentiRegistratiDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<String> cognome, ArrayList<String> email,
                                  ArrayList<String> password, ArrayList<String> idHackathon, ArrayList<String> idTeam) throws SQLException;

    void addUtenteRegistratoDB(String id,String nome, String cognome, String email, String password) throws SQLException;


    void setUtenteToGiudiceDB(String idUtente,String idGiudice,String idHackathon)throws SQLException;
    void setGiudiceToUtenteDB(String idUtente,String idGiudice)throws SQLException;
    void setUtenteToPartecipanteDB(String idUtente,String idPartecipante,String idHackathon, String idTeam)throws SQLException;


    //TEAMS
    void getAllTeamsDB(ArrayList<String> id,ArrayList<String> nome, ArrayList<String> codAccesso
            , ArrayList<Float> voti, ArrayList<String> idHackathon) throws SQLException;
    void addTeamsDB(String id, String nome, String codAccesso, String idHackathon) throws SQLException;
    void setVotoTeam(String idTeam, float voto)throws SQLException;
    void setCommentoProgresso(String idTeam,String nomeProgresso, String commento) throws SQLException;



    //PROGRESSI
    void getProgressiTeam(ArrayList<String> nome,ArrayList<String> codice, ArrayList<String> commento,
                          String idTeam) throws SQLException;
    void addProgresso(String idTeam,String nomeProgresso, String codice) throws SQLException;


    //HACKATHON
    void  addHackathonDB(String id,String nome,String sede, int durata) throws SQLException;
    void removeHackathonDB(String id) throws SQLException;
    public void getAllHackathonDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<Integer> nMaxPartec, ArrayList<Integer> numTeam, ArrayList<String> problema,
                                  ArrayList<Boolean> teamSuff, ArrayList<Boolean> viewProblema, ArrayList<Boolean> eventoFinito, ArrayList<Boolean> votazConclusa,
                                  ArrayList<Boolean> classPubblicata, ArrayList<String> sede, ArrayList<Integer> durata) throws SQLException;
    void setHackathonProblema(String idHackathon,String problema) throws SQLException;


    //FLAGS HACKATHON
    void setTeamSuff(boolean teamSuff, String idHackathon)throws SQLException;
    void setViewProblema(boolean viewProblema,String idHackathon)throws SQLException;
    void setEventoFinito(boolean eventoFinito,String idHackathon)throws SQLException;
    void setVotazConclusa(boolean votazConclusa, String idHackathon)throws SQLException;
    void setClassPubblicata(boolean classPubblicata, String idHackathon)throws SQLException;


    //FLAGS PIATTAFORMA

    boolean[] getAllFlagsPiattaforma()throws SQLException;
    void setOpenIscr(boolean openIscr)throws SQLException;
    void setEventoPronto(boolean eventoPronto)throws SQLException;

    //RICHIESTE GIUDICE
    void salvaRichiestaDB(String idUtente,String idHackathon, String richiesta)throws SQLException;
    void eliminaRichiestaGiudice(String idUtente) throws SQLException;
    void getAllRichiesteGiudiciDB(ArrayList<String> idUtente, ArrayList<String> richieste, ArrayList<String> idHackathon) throws SQLException;


    //COSTANTI ID
    int[] getCostantiID() throws SQLException;
    void storeNH(int nH) throws SQLException;
    void storeNU(int nU) throws SQLException;
    void storeNG(int nG) throws SQLException;
    void storeNP(int nP) throws SQLException;
    void storeNT(int nT) throws SQLException;









}

