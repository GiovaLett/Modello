package org.example.ImplementazionePostgresDAO;

import org.example.ConnessioneDB.ConnessioneDB;
import org.example.DAO.HackathonDao;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class HackathonDAOImplement implements HackathonDao {

    private Connection conn;



    public HackathonDAOImplement()throws SQLException
    {conn=ConnessioneDB.getInstance();}

    @Override
    public void addUtenteRegistratoDB(String id, String nome, String cognome, String email,
                                      String password) throws SQLException {

        conn=ConnessioneDB.getInstance();

        String sql="INSERT INTO \"UtentiRegistrati\" "+
                "(id,nome,cognome,email,password) VALUES ('"+id+"','"+nome+"','"+cognome+"','"+email+"','"+password+"')";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.executeUpdate();


        } catch (SQLException exc) {
            System.out.println("Errore nell'aggiunta a database");
            exc.printStackTrace();
        }

        conn.close();


    }




    @Override
    public void getAllUtentiRegistratiDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<String> cognome, ArrayList<String> email,
                                         ArrayList<String> password, ArrayList<String> idHackathon, ArrayList<String> idTeam) throws SQLException {

        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"UtentiRegistrati\" ";

        try{
            PreparedStatement prepStat = conn.prepareStatement(sql);
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

            //Da rivedere poi a casa

        }
        catch (Exception e){e.printStackTrace();}

        conn.close();


    }


    public void setUtenteToGiudiceDB(String idUtente,String idGiudice,String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"UtentiRegistrati\" SET  id=?, \"idHackathon\"=? WHERE id=?";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,idGiudice);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.setString(3,idUtente);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();


    }

    public void setGiudiceToUtenteDB(String idUtente,String idGiudice)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"UtentiRegistrati\" SET  id=?, \"idHackathon\"=? WHERE id=?";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,idUtente);
            preparedStatement.setString(2,null);
            preparedStatement.setString(3,idGiudice);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();


    }



    public void setUtenteToPartecipanteDB(String idUtente,String idPartecipante,String idHackathon, String idTeam)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"UtentiRegistrati\" SET  id=?, \"idHackathon\"=?,\"idTeam\"=? WHERE id=?";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,idPartecipante);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.setString(3,idTeam);
            preparedStatement.setString(4,idUtente);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();


    }


    /**TEAMS*/

    public void getAllTeamsDB(ArrayList<String> id,ArrayList<String> nome, ArrayList<String> cod_accesso
                             , ArrayList<Float> voti, ArrayList<String> idHackathon) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"Teams\" ";

        try{
            PreparedStatement prepStat = conn.prepareStatement(sql);
            ResultSet row= prepStat.executeQuery();

            while(row.next()){
                id.add(0,row.getString("id"));   //getNString si usa per leggere anche altri caratteri speciali, emoji ecc
                nome.add(0,row.getString("nome"));
                cod_accesso.add(0, row.getString("codice_accesso"));
                voti.add(0,row.getFloat("voto") );
                idHackathon.add(0,row.getString("id_hackathon") );

            }
            row.close();

            //Da rivedere poi a casa

        }
        catch (Exception e){e.printStackTrace();}

        conn.close();

    }


    public void addTeamsDB(String id, String nome, String cod_accesso, String idHackathon) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="INSERT INTO \"Teams\"  (id, nome, codice_accesso,id_hackathon) VALUES" +
                "(?,?,?,?)";
        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,nome);
            preparedStatement.setString(3,cod_accesso);

            preparedStatement.setString(4,idHackathon);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {

            ex.printStackTrace();
        }
        conn.close();

    }

    public void setVotoTeam(String idTeam, float voto)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Teams\" SET voto=?  WHERE id=?";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setFloat(1,voto);
            preparedStatement.setString(2,idTeam);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        conn.close();

    }

    /** PROGRESSI*/

    public void getProgressiTeam(ArrayList<String> nome,ArrayList<String> codice, ArrayList<String> commento,
                                String idTeam) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="SELECT * FROM \"Progressi\" WHERE \"id_team\"=?";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
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
        conn.close();
    }

    public void addProgresso(String idTeam,String nomeProgresso, String codice) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="INSERT INTO \"Progressi\"  ( codice,id_team, nome) VALUES" +
                "(?,?,?)";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,codice);
            preparedStatement.setString(2,idTeam);
            preparedStatement.setString(3,nomeProgresso);
            preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        conn.close();

    }



    public void setCommentoProgresso(String idTeam,String nomeProgresso, String commento) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Progressi\" SET commento=?  WHERE id_team=? AND nome=? ";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,commento);
            preparedStatement.setString(2,idTeam);
            preparedStatement.setString(3,nomeProgresso);
            preparedStatement.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
        conn.close();

    }




    /**HACKATHON
     *

     */
    @Override
    public void addHackathonDB(String id, String nome) throws SQLException {

        conn=ConnessioneDB.getInstance();

        String sql="INSERT INTO \"Hackathon\" "+
                "(id,nome) VALUES ('"+id+"','"+nome+"')";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.executeUpdate();


        } catch (SQLException exc) {
            System.out.println("Errore nell'aggiunta a database");
            exc.printStackTrace();
        }

        conn.close();
    }

    @Override
    public void removeHackathonDB(String id) throws SQLException {

        conn=ConnessioneDB.getInstance();

        String query="DELETE FROM \"Hackathon\" WHERE id=?";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();
    }

    @Override
    public void getAllHackathonDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<Integer> n_max_partec, ArrayList<Integer> n_team, ArrayList<String> problema,
                                  ArrayList<Boolean> team_suff, ArrayList<Boolean> view_problema, ArrayList<Boolean> evento_finito, ArrayList<Boolean> votaz_conclusa) throws SQLException {


        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"Hackathon\" ";

        try{
            PreparedStatement prepStat = conn.prepareStatement(sql);
            ResultSet row= prepStat.executeQuery();

            while(row.next()){
                id.add(0,row.getString("id"));   //getNString si usa per leggere anche altri caratteri speciali, emoji ecc
                nome.add(0,row.getString("nome"));
                n_team.add(0,row.getInt("n_team"));
                problema.add(0, row.getString("problema"));
                team_suff.add(0,row.getBoolean("team_suff"));
                view_problema.add(0,row.getBoolean("view_problema"));
                evento_finito.add(0,row.getBoolean("evento_finito"));
                votaz_conclusa.add(0,row.getBoolean("votaz_conclusa"));
            }
            row.close();

            //Da rivedere poi a casa

        }
        catch (Exception e){e.printStackTrace();}

        conn.close();
    }

    public void setHackathonProblema(String idHackathon,String problema) throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET problema=? WHERE id=?";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,problema);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        conn.close();


    }






    /**FLAGS HACKATHON*/

    public void setTeamSuff(boolean teamSuff, String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  team_suff=? WHERE id=? ";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setBoolean(1,teamSuff);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }

    public void setViewProblema(boolean viewProblema,String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  view_problema=? WHERE id=? ";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setBoolean(1,viewProblema);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }


    public void setEventoFinito(boolean eventoFinito,String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  evento_finito=? WHERE id=? ";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setBoolean(1,eventoFinito);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }


    public void setVotazConclusa(boolean votazConclusa, String idHackathon)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"Hackathon\" SET  votaz_conclusa=? WHERE id=? ";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setBoolean(1, votazConclusa);
            preparedStatement.setString(2,idHackathon);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }






    /**FLAGS PIATTAFORMA*/

    public boolean[] getAllFlagsPiattaforma()throws SQLException{

        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"FlagsPiattaforma\" ";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            ResultSet row= preparedStatement.executeQuery();
            row.next();
            boolean openIscr=row.getBoolean("open_iscr");
            boolean eventoPronto=row.getBoolean("evento_pronto");

            boolean[]flags={openIscr,eventoPronto};
            row.close();

            conn.close();
            return flags;

        }catch (Exception ex)
        {
            ex.printStackTrace();
            throw new SQLException("Errore caricamento flags piattaforma");
        }



    }

    public void setOpenIscr(boolean openIscr)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"FlagsPiattaforma\" SET  open_iscr=? ";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setBoolean(1,openIscr);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.close();

    }

    public void setEventoPronto(boolean eventoPronto)throws SQLException{

        conn=ConnessioneDB.getInstance();

        String query="UPDATE \"FlagsPiattaforma\" SET  evento_pronto=? ";

        try{
            PreparedStatement preparedStatement=conn.prepareStatement(query);
            preparedStatement.setBoolean(1,eventoPronto);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        conn.close();

    }



/**RichiesteGiudici*/

public void salvaRichiestaDB(String idUtente,String idHackathon, String richiesta)throws SQLException{

    conn=ConnessioneDB.getInstance();

    String query="INSERT INTO \"RichiesteGiudici\" "+
            "(id_utente,richiesta,id_hackathon) VALUES (?,?,?)";

    try{
        PreparedStatement preparedStatement= conn.prepareStatement(query);
        preparedStatement.setString(1,idUtente);
        preparedStatement.setString(2,richiesta);
        preparedStatement.setString(3,idHackathon);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    conn.close();
}


public void eliminaRichiestaGiudice(String idUtente) throws SQLException{

    conn=ConnessioneDB.getInstance();

    String query="DELETE FROM \"RichiesteGiudici\" WHERE id_utente=?";

    try{
        PreparedStatement preparedStatement= conn.prepareStatement(query);
        preparedStatement.setString(1,idUtente);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    conn.close();
}




public void getAllRichiesteGiudiciDB(ArrayList<String> idUtente, ArrayList<String> richieste, ArrayList<String> idHackathon) throws SQLException
{
    conn=ConnessioneDB.getInstance();

    String query="SELECT * FROM \"RichiesteGiudici\" ";

    try {
        PreparedStatement preparedStatement=conn.prepareStatement(query);
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





    /**COSTANTI ID*/
    @Override
    public int[] getCostantiID() throws SQLException{

        conn=ConnessioneDB.getInstance();

        String sql="SELECT * FROM \"CostantiId\" ";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
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


    public void storeNH(int nH) throws SQLException{
       conn=ConnessioneDB.getInstance();

       String sql="UPDATE \"CostantiId\" SET nh=? ";

       try{
           PreparedStatement preparedStatement= conn.prepareStatement(sql);
           preparedStatement.setInt(1,nH);
           preparedStatement.executeUpdate();

       } catch (SQLException e) {
           e.printStackTrace();
       }
       conn.close();
   }

    public void storeNU(int nU) throws SQLException{
        conn=ConnessioneDB.getInstance();

        String sql="UPDATE \"CostantiId\" SET  nu=? ";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setInt(1,nU);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }


    public void storeNG(int nG) throws SQLException{
        conn=ConnessioneDB.getInstance();

        String sql="UPDATE \"CostantiId\" SET  ng=? ";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setInt(1,nG);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }

    public void storeNP(int nP) throws SQLException{
        conn=ConnessioneDB.getInstance();

        String sql="UPDATE \"CostantiId\" SET  np=? ";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setInt(1,nP);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }


    public void storeNT(int nT) throws SQLException{
        conn=ConnessioneDB.getInstance();

        String sql="UPDATE \"CostantiId\" SET  nt=? ";

        try{
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setInt(1,nT);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }


}
