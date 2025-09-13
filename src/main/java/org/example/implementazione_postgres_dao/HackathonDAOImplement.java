package org.example.implementazione_postgres_dao;

import org.example.connessione_db.ConnessioneDB;
import org.example.dao.HackathonDao;

import java.sql.*;
import java.util.ArrayList;

public class HackathonDAOImplement implements HackathonDao {

    private Connection conn;


    /**
     * Costruttore della classe {@code HackathonDAOImplement}.
     * Inizializza la connessione al database utilizzando la classe {@code ConnessioneDB}.
     *
     * @throws SQLException se si verifica un errore durante l'ottenimento della connessione al database
     */
    public HackathonDAOImplement()throws SQLException
    {conn=ConnessioneDB.getInstance();}


    /**
     * Inserisce un nuovo utente registrato all'interno della tabella {@code UtentiRegistrati} del database.
     * I dati dell'utente vengono passati come parametri e salvati tramite una query SQL di tipo {@code INSERT}.
     *
     * @param id identificativo univoco dell'utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email indirizzo email dell'utente
     * @param password password associata all'utente
     * @throws SQLException se si verifica un errore durante la preparazione o l'esecuzione della query,
     *                      oppure nella chiusura delle risorse
     */
    @Override
    public void addUtenteRegistratoDB(String id, String nome, String cognome, String email,
                                      String password) throws SQLException {

        conn=ConnessioneDB.getInstance();

        String sql="INSERT INTO \"UtentiRegistrati\" "+
                "(id,nome,cognome,email,password) VALUES (?,?,?,?,?)";

        PreparedStatement preparedStatement=null;
        try{
             preparedStatement=conn.prepareStatement(sql);
             preparedStatement.setString(1,id);
             preparedStatement.setString(2,nome);
             preparedStatement.setString(3,cognome);
             preparedStatement.setString(4,email);
             preparedStatement.setString(5,password);
            preparedStatement.executeUpdate();


        } catch (SQLException exc) {
            System.out.println("Errore nell'aggiunta a database");
            exc.printStackTrace();
        }
        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();


    }



    /**
     * Recupera tutti gli utenti registrati dalla tabella {@code UtentiRegistrati} del database.
     * I risultati della query vengono salvati all'interno delle liste fornite come parametri,
     * ognuna corrispondente a un attributo dell'utente (id, nome, cognome, ecc.).
     * <p>
     * NB: i valori vengono inseriti all'inizio delle liste (indice 0), spostando in avanti gli elementi esistenti.
     * </p>
     *
     * @param id lista in cui verranno inseriti gli identificativi degli utenti
     * @param nome lista in cui verranno inseriti i nomi degli utenti
     * @param cognome lista in cui verranno inseriti i cognomi degli utenti
     * @param email lista in cui verranno inseriti gli indirizzi email degli utenti
     * @param password lista in cui verranno inserite le password degli utenti
     * @param idHackathon lista in cui verranno inseriti gli identificativi degli hackathon associati agli utenti
     * @param idTeam lista in cui verranno inseriti gli identificativi dei team associati agli utenti
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void getAllUtentiRegistratiDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<String> cognome, ArrayList<String> email,
                                         ArrayList<String> password, ArrayList<String> idHackathon, ArrayList<String> idTeam) throws SQLException {

        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"UtentiRegistrati\" ";

        PreparedStatement prepStat=null;

        try{
             prepStat = conn.prepareStatement(sql);
            ResultSet row= prepStat.executeQuery();

            while(row.next()){
                id.add(0,row.getString("id"));   //getNString si usa per leggere anche altri caratteri speciali, emoji ecc
                nome.add(0,row.getString("nome"));
                cognome.add(0, row.getString("cognome"));
                email.add(0, row.getString("email"));
                password.add(0,row.getString("password") );
                idHackathon.add(0,row.getString("idHackathon") );
                idTeam.add(0,row.getString("idTeam") );

            }
            row.close();



        }
        catch (Exception e){e.printStackTrace();}

        if(prepStat!=null){
            prepStat.close();
        }
        conn.close();


    }

    /**
     * Aggiorna un utente registrato nel database, assegnandogli il ruolo di giudice
     * per un determinato hackathon. L'operazione consiste nell'aggiornamento della riga
     * corrispondente all'utente specificato, modificando il suo identificativo e
     * associandolo all'hackathon indicato.
     *
     * @param idUtente identificativo dell'utente da aggiornare
     * @param idGiudice nuovo identificativo da assegnare all'utente come giudice
     * @param idHackathon identificativo dell'hackathon a cui l'utente/giudice è associato
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void setUtenteToGiudiceDB(String idUtente,String idGiudice,String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"UtentiRegistrati\" SET  id=?, \"idHackathon\"=? WHERE id=?";

        PreparedStatement preparedStatement=null;
        try{
             preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,idGiudice);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.setString(3,idUtente);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();


    }


    /**
     * Converte un giudice nuovamente in utente all'interno della tabella {@code UtentiRegistrati}.
     * L'operazione aggiorna la riga corrispondente al giudice, sostituendo il suo identificativo
     * con quello dell'utente e rimuovendo l'associazione con l'hackathon (campo impostato a {@code null}).
     *
     * @param idUtente identificativo da assegnare all'utente
     * @param idGiudice identificativo corrente del giudice da aggiornare
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void setGiudiceToUtenteDB(String idUtente,String idGiudice)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"UtentiRegistrati\" SET  id=?, \"idHackathon\"=? WHERE id=?";

        PreparedStatement preparedStatement=null;

        try{
            preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,idUtente);
            preparedStatement.setString(2,null);
            preparedStatement.setString(3,idGiudice);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();


    }


    /**
     * Aggiorna un utente registrato convertendolo in partecipante di un determinato hackathon.
     * L'operazione aggiorna l'identificativo dell'utente, associa l'utente all'hackathon specificato
     * e assegna il partecipante a un team.
     *
     * @param idUtente identificativo dell'utente da aggiornare
     * @param idPartecipante nuovo identificativo da assegnare come partecipante
     * @param idHackathon identificativo dell'hackathon a cui l'utente/partecipante è associato
     * @param idTeam identificativo del team a cui il partecipante appartiene
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void setUtenteToPartecipanteDB(String idUtente,String idPartecipante,String idHackathon, String idTeam)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"UtentiRegistrati\" SET  id=?, \"idHackathon\"=?,\"idTeam\"=? WHERE id=?";

        PreparedStatement preparedStatement=null;
        try{
             preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,idPartecipante);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.setString(3,idTeam);
            preparedStatement.setString(4,idUtente);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();


    }


    /**
     * Recupera tutti i team dalla tabella {@code Teams} del database.
     * I risultati della query vengono salvati nelle liste passate come parametri,
     * ognuna delle quali rappresenta un attributo del team (id, nome, codice di accesso, voto, hackathon associato).
     * <p>
     * NB: i valori vengono inseriti all'inizio delle liste (indice 0), spostando in avanti gli elementi già presenti.
     * </p>
     *
     * @param id lista in cui verranno inseriti gli identificativi dei team
     * @param nome lista in cui verranno inseriti i nomi dei team
     * @param codAccesso lista in cui verranno inseriti i codici di accesso dei team
     * @param voti lista in cui verranno inseriti i voti dei team
     * @param idHackathon lista in cui verranno inseriti gli identificativi degli hackathon associati ai team
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void getAllTeamsDB(ArrayList<String> id,ArrayList<String> nome, ArrayList<String> codAccesso
                             , ArrayList<Float> voti, ArrayList<String> idHackathon) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"Teams\" ";

        PreparedStatement preparedStatement=null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            ResultSet row= preparedStatement.executeQuery();

            while(row.next()){
                id.add(0,row.getString("id"));   //getNString si usa per leggere anche altri caratteri speciali, emoji ecc
                nome.add(0,row.getString("nome"));
                codAccesso.add(0, row.getString("codice_accesso"));
                voti.add(0,row.getFloat("voto") );
                idHackathon.add(0,row.getString("id_hackathon") );

            }
            row.close();


        }
        catch (Exception e){e.printStackTrace();}

        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();

    }


    /**
     * Inserisce un nuovo team nella tabella {@code Teams} del database.
     * I dati del team (id, nome, codice di accesso e hackathon associato)
     * vengono salvati tramite una query SQL di tipo {@code INSERT}.
     *
     * @param id identificativo univoco del team
     * @param nome nome del team
     * @param codAccesso codice di accesso associato al team
     * @param idHackathon identificativo dell'hackathon a cui il team partecipa
     * @throws SQLException se si verifica un errore durante la preparazione o l'esecuzione della query,
     *                      oppure nella chiusura delle risorse
     */
    @Override
    public void addTeamsDB(String id, String nome, String codAccesso, String idHackathon) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="INSERT INTO \"Teams\"  (id, nome, codice_accesso,id_hackathon) VALUES" +
                "(?,?,?,?)";

        PreparedStatement preparedStatement=null;

        try{
            preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,nome);
            preparedStatement.setString(3,codAccesso);

            preparedStatement.setString(4,idHackathon);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {

            ex.printStackTrace();
        }
        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();

    }


    /**
     * Aggiorna il voto di un team nella tabella {@code Teams} del database.
     * L'operazione modifica il campo {@code voto} per il team con l'identificativo specificato.
     *
     * @param idTeam identificativo del team da aggiornare
     * @param voto nuovo voto da assegnare al team
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void setVotoTeam(String idTeam, float voto)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Teams\" SET voto=?  WHERE id=?";

        PreparedStatement preparedStatement=null;

        try{
            preparedStatement= conn.prepareStatement(query);
            preparedStatement.setFloat(1,voto);
            preparedStatement.setString(2,idTeam);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();
    }

    /**
     * Recupera i progressi di un team specifico dalla tabella {@code Progressi} del database.
     * I risultati della query vengono salvati nelle liste passate come parametri,
     * ognuna corrispondente a un attributo del progresso (nome, codice, commento).
     * <p>
     * NB: i valori vengono inseriti all'inizio delle liste (indice 0), spostando in avanti gli elementi già presenti.
     * </p>
     *
     * @param nome lista in cui verranno inseriti i nomi dei progressi
     * @param codice lista in cui verranno inseriti i codici dei progressi
     * @param commento lista in cui verranno inseriti i commenti associati ai progressi
     * @param idTeam identificativo del team di cui recuperare i progressi
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void getProgressiTeam(ArrayList<String> nome,ArrayList<String> codice, ArrayList<String> commento,
                                String idTeam) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="SELECT * FROM \"Progressi\" WHERE \"id_team\"=?";

        PreparedStatement preparedStatement=null;

        try{
            preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,idTeam);
            ResultSet row= preparedStatement.executeQuery();

            while (row.next()){

                nome.add(0, row.getString("nome"));
                codice.add(0,row.getString("codice"));
                commento.add(0,row.getString("commento"));
            }
            row.close();
        } catch (Exception e) {

            e.printStackTrace();
        
        }
        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();
    }


    /**
     * Inserisce un nuovo progresso per un team nella tabella {@code Progressi} del database.
     * I dati del progresso includono il codice identificativo, il team associato e il nome del progresso.
     *
     * @param idTeam identificativo del team a cui il progresso appartiene
     * @param nomeProgresso nome del progresso da aggiungere
     * @param codice codice identificativo univoco del progresso
     * @throws SQLException se si verifica un errore durante la preparazione o l'esecuzione della query,
     *                      oppure nella chiusura delle risorse
     */
    @Override
    public void addProgresso(String idTeam,String nomeProgresso, String codice) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="INSERT INTO \"Progressi\"  ( codice,id_team, nome) VALUES" +
                "(?,?,?)";

        PreparedStatement preparedStatement=null;

        try{
            preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,codice);
            preparedStatement.setString(2,idTeam);
            preparedStatement.setString(3,nomeProgresso);
            preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();

    }

    /**
     * Aggiorna il commento associato a un progresso specifico di un team nella tabella {@code Progressi}.
     * L'operazione individua il progresso tramite l'identificativo del team e il nome del progresso,
     * e aggiorna il campo {@code commento} con il valore fornito.
     *
     * @param idTeam identificativo del team a cui appartiene il progresso
     * @param nomeProgresso nome del progresso da aggiornare
     * @param commento nuovo commento da associare al progresso
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void setCommentoProgresso(String idTeam,String nomeProgresso, String commento) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Progressi\" SET commento=?  WHERE id_team=? AND nome=? ";

        PreparedStatement preparedStatement=null;

        try{
           preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,commento);
            preparedStatement.setString(2,idTeam);
            preparedStatement.setString(3,nomeProgresso);
            preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();

    }




    /**
     * Inserisce un nuovo hackathon nella tabella {@code Hackathon} del database.
     * I dati dell'hackathon includono l'identificativo univoco, il nome, la sede e la durata in giorni.
     *
     * @param id identificativo univoco dell'hackathon
     * @param nome nome dell'hackathon
     * @param sede sede in cui si svolge l'hackathon
     * @param durata durata dell'hackathon espressa in giorni
     * @throws SQLException se si verifica un errore durante la preparazione o l'esecuzione della query,
     *                      oppure nella chiusura delle risorse
     */
    @Override
    public void addHackathonDB(String id, String nome,String sede,int durata) throws SQLException {

        conn=ConnessioneDB.getInstance();

        String sql="INSERT INTO \"Hackathon\" "+
                "(id,nome,sede,durata)  VALUES (?,?,?,?)";



        PreparedStatement preparedStatement=null;

        try{
            preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,nome);
            preparedStatement.setString(3,sede);
            preparedStatement.setInt(4,durata);
            preparedStatement.executeUpdate();


        } catch (SQLException exc) {
            System.out.println("Errore nell'aggiunta a database");
            exc.printStackTrace();
        }

        if(preparedStatement!=null)
            preparedStatement.close();
        conn.close();
    }


    /**
     * Rimuove un hackathon dalla tabella {@code Hackathon} del database in base al suo identificativo.
     * L'operazione elimina la riga corrispondente all'hackathon specificato.
     *
     * @param id identificativo dell'hackathon da rimuovere
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void removeHackathonDB(String id) throws SQLException {

        conn=ConnessioneDB.getInstance();

        String query="DELETE FROM \"Hackathon\" WHERE id=?";

        try(  PreparedStatement  preparedStatement= conn.prepareStatement(query);){

            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();
    }


    /**
     * Recupera tutti gli hackathon presenti nella tabella {@code Hackathon} del database.
     * I risultati della query vengono salvati nelle liste passate come parametri, ciascuna
     * corrispondente a un attributo dell'hackathon.
     * <p>
     * NB: i valori vengono inseriti all'inizio delle liste (indice 0), spostando in avanti gli elementi già presenti.
     * </p>
     *
     * @param id lista in cui verranno inseriti gli identificativi degli hackathon
     * @param nome lista in cui verranno inseriti i nomi degli hackathon
     * @param nMaxPartec lista in cui verranno inseriti i numeri massimi di partecipanti (non valorizzato nel codice attuale)
     * @param numTeam lista in cui verranno inseriti i numeri di team per ogni hackathon
     * @param problema lista in cui verranno inseriti i problemi assegnati agli hackathon
     * @param teamSuff lista in cui verranno inseriti i valori booleani che indicano se ci sono team sufficienti
     * @param viewProblema lista in cui verranno inseriti i valori booleani relativi alla visualizzazione del problema
     * @param eventoFinito lista in cui verranno inseriti i valori booleani che indicano se l'evento è terminato
     * @param votazConclusa lista in cui verranno inseriti i valori booleani che indicano se la votazione è conclusa
     * @param classPubblicata lista in cui verranno inseriti i valori booleani che indicano se la classifica è pubblicata
     * @param sede lista in cui verranno inserite le sedi degli hackathon
     * @param durata lista in cui verranno inserite le durate degli hackathon in giorni
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    @Override
    public void getAllHackathonDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<Integer> nMaxPartec, ArrayList<Integer> numTeam, ArrayList<String> problema,
                                  ArrayList<Boolean> teamSuff, ArrayList<Boolean> viewProblema, ArrayList<Boolean> eventoFinito, ArrayList<Boolean> votazConclusa,
                                  ArrayList<Boolean> classPubblicata, ArrayList<String> sede, ArrayList<Integer> durata) throws SQLException {


        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"Hackathon\" ";

        

        try(PreparedStatement prepStat = conn.prepareStatement(sql);){

            ResultSet row= prepStat.executeQuery();

            while(row.next()){
                id.add(0,row.getString("id"));   //getNString si usa per leggere anche altri caratteri speciali, emoji ecc
                nome.add(0,row.getString("nome"));
                numTeam.add(0,row.getInt("n_team"));
                problema.add(0, row.getString("problema"));
                teamSuff.add(0,row.getBoolean("team_suff"));
                viewProblema.add(0,row.getBoolean("view_problema"));
                eventoFinito.add(0,row.getBoolean("evento_finito"));
                votazConclusa.add(0,row.getBoolean("votaz_conclusa"));
                classPubblicata.add(0,row.getBoolean("classifica_pubblicata"));
                sede.add(0,row.getString("sede"));
                durata.add(0,row.getInt("durata"));
            }
            row.close();

            //Da rivedere poi a casa

        }
        catch (Exception e){e.printStackTrace();}

        conn.close();
    }


    /**
     * Imposta o aggiorna il problema associato a un hackathon specifico nella tabella {@code Hackathon}.
     * L'operazione aggiorna il campo {@code problema} per l'hackathon identificato dal parametro {@code idHackathon}.
     *
     * @param idHackathon identificativo dell'hackathon da aggiornare
     * @param problema descrizione del problema da assegnare all'hackathon
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void setHackathonProblema(String idHackathon,String problema) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET problema=? WHERE id=?";

        try( PreparedStatement preparedStatement= conn.prepareStatement(query);){

            preparedStatement.setString(1,problema);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();


    }






    /**
     * Aggiorna lo stato della disponibilità sufficiente di team per un hackathon specifico.
     * L'operazione modifica il campo {@code team_suff} nella tabella {@code Hackathon}.
     *
     * @param teamSuff valore booleano che indica se ci sono team sufficienti per l'hackathon
     * @param idHackathon identificativo dell'hackathon da aggiornare
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void setTeamSuff(boolean teamSuff, String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  team_suff=? WHERE id=? ";

        try(PreparedStatement preparedStatement=conn.prepareStatement(query);){

            preparedStatement.setBoolean(1,teamSuff);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }

    /**
     * Aggiorna lo stato della visualizzazione del problema per un hackathon specifico.
     * L'operazione modifica il campo {@code view_problema} nella tabella {@code Hackathon}.
     *
     * @param viewProblema valore booleano che indica se il problema è visibile ai partecipanti
     * @param idHackathon identificativo dell'hackathon da aggiornare
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */

    @Override
    public void setViewProblema(boolean viewProblema,String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  view_problema=? WHERE id=? ";

        try(PreparedStatement preparedStatement=conn.prepareStatement(query)){

            preparedStatement.setBoolean(1,viewProblema);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }


    /**
     * Aggiorna lo stato di completamento di un hackathon specifico.
     * L'operazione modifica il campo {@code evento_finito} nella tabella {@code Hackathon}.
     *
     * @param eventoFinito valore booleano che indica se l'hackathon è terminato
     * @param idHackathon identificativo dell'hackathon da aggiornare
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void setEventoFinito(boolean eventoFinito,String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  evento_finito=? WHERE id=? ";

        try( PreparedStatement preparedStatement=conn.prepareStatement(query);){

            preparedStatement.setBoolean(1,eventoFinito);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }



    /**
     * Aggiorna lo stato di completamento della votazione per un hackathon specifico.
     * L'operazione modifica il campo {@code votaz_conclusa} nella tabella {@code Hackathon}.
     *
     * @param votazConclusa valore booleano che indica se la votazione è stata completata
     * @param idHackathon identificativo dell'hackathon da aggiornare
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void setVotazConclusa(boolean votazConclusa, String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  votaz_conclusa=? WHERE id=? ";

        try(PreparedStatement preparedStatement=conn.prepareStatement(query);){

            preparedStatement.setBoolean(1, votazConclusa);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }


    /**
     * Aggiorna lo stato di pubblicazione della classifica per un hackathon specifico.
     * L'operazione modifica il campo {@code classifica_pubblicata} nella tabella {@code Hackathon}.
     *
     * @param classPubblicata valore booleano che indica se la classifica è stata pubblicata
     * @param idHackathon identificativo dell'hackathon da aggiornare
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void setClassPubblicata(boolean classPubblicata, String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  classifica_pubblicata=? WHERE id=? ";

        try(PreparedStatement preparedStatement=conn.prepareStatement(query);){

            preparedStatement.setBoolean(1, classPubblicata);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{conn.close();}






    }






    /**
     * Recupera lo stato delle flags della piattaforma dalla tabella {@code FlagsPiattaforma}.
     * Restituisce un array di booleani rappresentante le flags principali della piattaforma:
     * <ul>
     *     <li>{@code open_iscr} - indica se le iscrizioni sono aperte</li>
     *     <li>{@code evento_pronto} - indica se l'evento è pronto</li>
     * </ul>
     *
     * @return array di booleani contenente le flags della piattaforma
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public boolean[] getAllFlagsPiattaforma()throws SQLException{

        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"FlagsPiattaforma\" ";

        try(PreparedStatement preparedStatement= conn.prepareStatement(sql);){

            ResultSet row= preparedStatement.executeQuery();
            row.next();
            boolean openIscr=row.getBoolean("open_iscr");
            boolean eventoPronto=row.getBoolean("evento_pronto");

            boolean[]flags={openIscr,eventoPronto};
            row.close();


            return flags;

        }catch (Exception ex)
        {
            ex.printStackTrace();
            throw new SQLException("Errore caricamento flags piattaforma");
        }
        finally{    conn.close();   }



    }

    /**
     * Recupera la data dell'evento dalla tabella {@code FlagsPiattaforma}.
     *
     * @return la data dell'evento come {@code String}
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura delle risorse
     */
    public String getDataEvento() throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="SELECT * FROM \"FlagsPiattaforma\" ";

        PreparedStatement preparedStatement=null;
        try{
            preparedStatement=conn.prepareStatement(query);
            ResultSet row=preparedStatement.executeQuery();
            row.next();

            String dataEvento=row.getString("data_evento");
            row.close();
            return dataEvento;

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        }
        finally{
            if(preparedStatement!=null)
                preparedStatement.close();
            conn.close();
        }
    }


    /**
     * Imposta o aggiorna la data dell'evento nella tabella {@code FlagsPiattaforma}.
     *
     * @param dataEvento data dell'evento da impostare, come {@code String}
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    public void setDataEvento(String dataEvento) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"FlagsPiattaforma\" SET  data_evento=? ";
        try(PreparedStatement preparedStatement=conn.prepareStatement(query);){

            preparedStatement.setString(1,dataEvento);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally{    conn.close();   }
    }

    /**
     * Imposta lo stato delle iscrizioni sulla piattaforma aggiornando la tabella {@code FlagsPiattaforma}.
     *
     * @param openIscr valore booleano che indica se le iscrizioni sono aperte
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void setOpenIscr(boolean openIscr)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"FlagsPiattaforma\" SET  open_iscr=? ";

        try(PreparedStatement preparedStatement=conn.prepareStatement(query);){

            preparedStatement.setBoolean(1,openIscr);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally{    conn.close();   }

    }


    /**
     * Aggiorna lo stato dell'evento sulla piattaforma, impostando se l'evento è pronto,
     * modificando il campo {@code evento_pronto} nella tabella {@code FlagsPiattaforma}.
     *
     * @param eventoPronto valore booleano che indica se l'evento è pronto
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void setEventoPronto(boolean eventoPronto)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"FlagsPiattaforma\" SET  evento_pronto=? ";

        try(PreparedStatement preparedStatement=conn.prepareStatement(query);){

            preparedStatement.setBoolean(1,eventoPronto);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        conn.close();

    }




    /**
     * Salva una richiesta inviata da un utente per diventare giudice di un hackathon.
     * L'operazione inserisce una nuova riga nella tabella {@code RichiesteGiudici}.
     *
     * @param idUtente identificativo dell'utente che invia la richiesta
     * @param idHackathon identificativo dell'hackathon a cui è destinata la richiesta
     * @param richiesta testo della richiesta inviata dall'utente
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
@Override
public void salvaRichiestaDB(String idUtente,String idHackathon, String richiesta)throws SQLException{

    conn=ConnessioneDB.getInstance();

    String query="INSERT INTO \"RichiesteGiudici\" "+
            "(id_utente,richiesta,id_hackathon) VALUES (?,?,?)";

    try(PreparedStatement preparedStatement= conn.prepareStatement(query);){

        preparedStatement.setString(1,idUtente);
        preparedStatement.setString(2,richiesta);
        preparedStatement.setString(3,idHackathon);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    conn.close();
}


    /**
     * Elimina una richiesta di un utente per diventare giudice dalla tabella {@code RichiesteGiudici}.
     *
     * @param idUtente identificativo dell'utente la cui richiesta deve essere rimossa
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
@Override
public void eliminaRichiestaGiudice(String idUtente) throws SQLException{

    conn=ConnessioneDB.getInstance();

    String query="DELETE FROM \"RichiesteGiudici\" WHERE id_utente=?";

    try( PreparedStatement preparedStatement= conn.prepareStatement(query);){

        preparedStatement.setString(1,idUtente);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    conn.close();
}


    /**
     * Recupera tutte le richieste degli utenti per diventare giudici dalla tabella {@code RichiesteGiudici}.
     * I dati estratti vengono salvati nelle liste fornite come parametri.
     * <p>
     * NB: i valori vengono inseriti all'inizio delle liste (indice 0), spostando in avanti gli elementi già presenti.
     * </p>
     *
     * @param idUtente lista in cui verranno inseriti gli identificativi degli utenti che hanno inviato richieste
     * @param richieste lista in cui verranno inserite le richieste testuali degli utenti
     * @param idHackathon lista in cui verranno inseriti gli identificativi degli hackathon a cui le richieste sono associate
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
@Override
public void getAllRichiesteGiudiciDB(ArrayList<String> idUtente, ArrayList<String> richieste, ArrayList<String> idHackathon) throws SQLException
{
    conn=ConnessioneDB.getInstance();

    String query="SELECT * FROM \"RichiesteGiudici\" ";

    try (PreparedStatement preparedStatement=conn.prepareStatement(query);){

        ResultSet row= preparedStatement.executeQuery();

        while(row.next()){
            idUtente.add(0,row.getString("id_utente"));
            richieste.add(0,row.getString("richiesta"));
            idHackathon.add(0,row.getString("id_hackathon"));
        }
        row.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
    conn.close();

}





    /**
     * Recupera i valori delle costanti identificative dalla tabella {@code CostantiId}.
     * Le costanti rappresentano numeri chiave relativi a hackathon, team, utenti, progressi e giudici.
     *
     * @return array di interi contenente le costanti nell'ordine: {@code nh}, {@code nt}, {@code nu}, {@code np}, {@code ng}
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public int[] getCostantiID() throws SQLException{

        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"CostantiId\" ";

        try(PreparedStatement preparedStatement= conn.prepareStatement(sql);){

            ResultSet row= preparedStatement.executeQuery();
            row.next();
            int nH=row.getInt("nh");
            int nT=row.getInt("nt");
            int nU=row.getInt("nu");
            int nP=row.getInt("np");
            int nG=row.getInt("ng");

            int[]costanti={nH,nT,nU,nP,nG};
            row.close();

            conn.close();
            return costanti;

        }catch (Exception ex)
        {  ex.printStackTrace();
            throw new SQLException("Errore caricamento costantiID");
        }
    }


    /**
     * Aggiorna il valore della costante {@code nh} nella tabella {@code CostantiId}.
     * Questa costante rappresenta un identificativo numerico legato agli hackathon.
     *
     * @param nH nuovo valore da memorizzare per la costante {@code nh}
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void storeNH(int nH) throws SQLException{
       conn=ConnessioneDB.getInstance();

       String sql="UPDATE \"CostantiId\" SET nh=? ";

       try( PreparedStatement preparedStatement= conn.prepareStatement(sql);){

           preparedStatement.setInt(1,nH);
           preparedStatement.executeUpdate();

       } catch (SQLException e) {
           e.printStackTrace();
       }
       conn.close();
   }


    /**
     * Aggiorna il valore della costante {@code nu} nella tabella {@code CostantiId}.
     * Questa costante rappresenta un identificativo numerico legato agli utenti.
     *
     * @param nU nuovo valore da memorizzare per la costante {@code nu}
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void storeNU(int nU) throws SQLException{
        conn=ConnessioneDB.getInstance();

        String sql="UPDATE \"CostantiId\" SET  nu=? ";

        try(PreparedStatement preparedStatement= conn.prepareStatement(sql);){

            preparedStatement.setInt(1,nU);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }


    /**
     * Aggiorna il valore della costante {@code ng} nella tabella {@code CostantiId}.
     * Questa costante rappresenta un identificativo numerico legato ai giudici.
     *
     * @param nG nuovo valore da memorizzare per la costante {@code ng}
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void storeNG(int nG) throws SQLException{
        conn=ConnessioneDB.getInstance();

        String sql="UPDATE \"CostantiId\" SET  ng=? ";

        try(PreparedStatement preparedStatement= conn.prepareStatement(sql);){

            preparedStatement.setInt(1,nG);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }


    /**
     * Aggiorna il valore della costante {@code np} nella tabella {@code CostantiId}.
     * Questa costante rappresenta un identificativo numerico legato ai progressi.
     *
     * @param nP nuovo valore da memorizzare per la costante {@code np}
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void storeNP(int nP) throws SQLException{
        conn=ConnessioneDB.getInstance();

        String sql="UPDATE \"CostantiId\" SET  np=? ";

        try( PreparedStatement preparedStatement= conn.prepareStatement(sql);){

            preparedStatement.setInt(1,nP);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }


    /**
     * Aggiorna il valore della costante {@code nt} nella tabella {@code CostantiId}.
     * Questa costante rappresenta un identificativo numerico legato ai team.
     *
     * @param nT nuovo valore da memorizzare per la costante {@code nt}
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o la chiusura della connessione
     */
    @Override
    public void storeNT(int nT) throws SQLException{
        conn=ConnessioneDB.getInstance();

        String sql="UPDATE \"CostantiId\" SET  nt=? ";

        try(PreparedStatement preparedStatement= conn.prepareStatement(sql);){

            preparedStatement.setInt(1,nT);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }


}
