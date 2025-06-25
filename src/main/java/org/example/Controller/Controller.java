package org.example.Controller;

import org.example.DAO.HackathonDao;
import org.example.ImplementazionePostgresDAO.HackathonDAOImplement;
import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;
import org.example.Model.Progresso;
import org.example.Model.ruoli.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller
{
    //per stabilire se scaricare o meno gli utenti registrati dal database



    Utente_registrato amministratore=new Organizzatore("0000","Giovanni","Lettieri","gio@email.it","password");

    Piattaforma piattaforma=new Piattaforma();
    Utente_registrato utenteCorrente;
    Hackathon hackathonCorrente;
    Team teamCorrente;

    public Controller(){

        piattaforma.addUtenteReg(amministratore);
    }



    /**
     * SET UTENTI
     */
    public Utente_registrato getUtenteCorrente() {return utenteCorrente;}

    public void setUtenteCorrente(Utente_registrato utenteCorrente) {
        this.utenteCorrente = utenteCorrente;
    }

    public void addUtentiProva(){
        piattaforma.addUtenteReg(new Utente_registrato("Italo","Calvino","italo@email.it","password"));
        piattaforma.addUtenteReg(new Utente_registrato("Francesco","Torvo","fra@email.it","password"));
        piattaforma.addUtenteReg(new Utente_registrato("Carlo","Magno","carlo@email.it","password"));
        piattaforma.addUtenteReg(new Utente_registrato("Sara","Pace","sara@email.it","password"));
        piattaforma.addUtenteReg(new Utente_registrato("Giulia","Casta","giulia@email.it","password"));
        piattaforma.addUtenteReg(new Utente_registrato("Samuel","Triff","samuel@email.it","password"));
    }


    public void addUtenteRegistrato(String nome,String cognome,String email,String password) throws SQLException
    {
        Utente_registrato nuovoUtente =new Utente_registrato(nome,cognome,email,password);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.addUtenteRegistratoDB(nuovoUtente.getID(), nuovoUtente.getNome(), nuovoUtente.getCognome(), nuovoUtente.getEmail(), nuovoUtente.getPassword());
        piattaforma.addUtenteReg(nuovoUtente);

        hackathonDAOImplement.storeNU(Utente_registrato.getnU());
    }


    public Utente_registrato findAccount(String email, String password) throws IllegalArgumentException{

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getEmail().equals(email) && utente.getPassword().equals(password))
                return utente;
        }
        throw new IllegalArgumentException("Credenziali errate");
    }

    public void getUtentiRegistratiDB()throws SQLException{

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        ArrayList<String>id=new ArrayList<>();
        ArrayList<String>nome=new ArrayList<>();
        ArrayList<String>cognome=new ArrayList<>();
        ArrayList<String>email=new ArrayList<>();
        ArrayList<String>password=new ArrayList<>();
        ArrayList<String>idHackathon=new ArrayList<>();
        ArrayList<String>idTeam=new ArrayList<>();

        hackathonDAOImplement.getAllUtentiRegistratiDB(id, nome, cognome, email, password, idHackathon,idTeam);

        for(int i=0;i<id.size();i++)
        {
            System.out.println(email.get(i)+"   idHack: "+idHackathon.get(i)+"   idTeam: "+idTeam.get(i));

            if(idHackathon.get(i)==null && idTeam.get(i)==null) {
                piattaforma.getListaUtenReg().add(
                        new Utente_registrato(id.get(i), nome.get(i), cognome.get(i), email.get(i), password.get(i)));
            }
            else if(idHackathon.get(i)!=null && idTeam.get(i)==null) {

                Giudice giudice=new Giudice(id.get(i), nome.get(i), cognome.get(i), email.get(i), password.get(i), idHackathon.get(i));
                piattaforma.getListaUtenReg().add(giudice);

                Hackathon hackathon=findHackId(giudice.getIDHackatlon());
                hackathon.addGiudice(giudice);

            }
            else {
                Partecipante partecipante= new Partecipante(id.get(i), nome.get(i), cognome.get(i), email.get(i), password.get(i), idTeam.get(i),idHackathon.get(i));
                piattaforma.getListaUtenReg().add(partecipante);

                Hackathon hackathon=findHackId(partecipante.getIDHackathon());
                Team team=findIDTeam(partecipante.getIDTeam(), hackathon);
                team.addPartecipante(partecipante);
            }
        }

        ArrayList<String> idUtente=new ArrayList<>();
        ArrayList<String> richiesta=new ArrayList<>();
        ArrayList<String> idHack=new ArrayList<>();

        hackathonDAOImplement.getAllRichiesteGiudiciDB(idUtente,richiesta,idHack);

        for(int i=0; i<idUtente.size(); i++){
            Utente_registrato utente=findIdUtenteReg(idUtente.get(i));
            if(utente!=null){
                utente.setRichiestaGiudice(richiesta.get(i));
                utente.setHackIDRichiesta(idHack.get(i));
            }
        }
    }

    public Utente_registrato findIdUtenteReg(String idUtente){

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getID().equals(idUtente))
                return utente;
        }
        return null;
    }



    public void getCostantiID() throws SQLException{

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement() ;

        int[] costanti= hackathonDAOImplement.getCostantiID();
        Hackathon.setnH(        costanti[0]);
        Team.setnT(             costanti[1]);
        Utente_registrato.setnU(costanti[2]);
        Partecipante.setnP(     costanti[3]);
        Giudice.setnG(          costanti[4]);



    }

    public void getFlagsPiattaforma() throws SQLException{

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        boolean[] flagsPiattaforma= hackathonDAOImplement.getAllFlagsPiattaforma();

        piattaforma.setOpen_iscr(  flagsPiattaforma[0]);
        piattaforma.setEventoPronto(  flagsPiattaforma[1]);
    }




    /**
 * OPERAZIONI SUI TEAM
 */
    public void setTeamCorrente(Team teamCorrente) {    this.teamCorrente = teamCorrente;}
    public Team getTeamCorrente() {return teamCorrente;}

    public void getAllTeamDB()throws SQLException{
        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> nome = new ArrayList<>();
        ArrayList<String> cod_accesso = new ArrayList<>();

        ArrayList<Float> voti = new ArrayList<>();
        ArrayList<String> idHackathon = new ArrayList<>();

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.getAllTeamsDB(id,nome,cod_accesso,voti,idHackathon);

        for(int i=0; i<id.size();i++){
            Team team=new Team(id.get(i),  nome.get(i), cod_accesso.get(i), voti.get(i), idHackathon.get(i));
            findHackId( team.getIDHackathon() ).addTeam(team);
            getProgressiTeamDB(team);

        }

    }


    private void getProgressiTeamDB(Team team) throws SQLException{

        ArrayList<String> nome = new ArrayList<>();
        ArrayList<String> codice=new ArrayList<>();
        ArrayList<String> commento=new ArrayList<>();

        HackathonDAOImplement hackathonDao=new HackathonDAOImplement();
        hackathonDao.getProgressiTeam(nome,codice,commento, team.getID());

        for(int i=0;i< nome.size();i++){

            Progresso progresso=new Progresso();
            progresso.setNome( nome.get(i));
            progresso.setCodiceProgresso(codice.get(i));
            progresso.setCommento(commento.get(i));
            progresso.setIdTeam(  team.getID());

            team.getArrayProgresso().add(0, progresso);
        }


    }

    public Team findIDTeam(String ID, Hackathon hackathon) throws IllegalArgumentException
    {
        for(Team team: hackathon.getListaTeam())
        {
            if(team.getID().equals(ID))
                return team;
        }
        throw new IllegalArgumentException("ID Team non trovato");
    }

    public Team findCodeAccessTeam(String codeAccess,Hackathon hackathon) throws IllegalArgumentException
    {
        for(Team team: hackathon.getListaTeam())
        {
            if(team.getCodiceAccesso().equals(codeAccess)) return team;
        }

        throw new IllegalArgumentException("Codice di accesso errato");
    }



    public void ordinaTeamVoti(Hackathon hackathon){ //Dal voto migliore (più alto) al peggiore (più basso) dunque decrescente

        Team maxTeam, corrTeam, ausTeam;
        int max;
        for(int i=0;i<hackathon.getListaTeam().size();i++)
        {
            max =i;
            maxTeam =hackathon.getListaTeam().get(i);

            for(int j=i;j<hackathon.getListaTeam().size();j++)
            {
                corrTeam=hackathon.getListaTeam().get(j);

                if(corrTeam.getVoto()> maxTeam.getVoto()){
                    maxTeam =corrTeam;
                    max =j;
                }
            }
            ausTeam=hackathon.getListaTeam().get(i);
            hackathon.getListaTeam().set(max,ausTeam);
            hackathon.getListaTeam().set(i, maxTeam);


        }

    }


    public boolean isNomeProgressoOriginale(String nomeProgresso){

        for(Progresso progresso: teamCorrente.getArrayProgresso()){

            if(progresso.getNome().equals(nomeProgresso))
                return false;
        }
        return true;
    }

    /**
     *  OPERAZIONE UTENTE REGISTRATO
     */


    //PRIMA DELLE ISCRIZIONI
    public void diventaGiudice() throws SQLException{

        Giudice nuovoGiudice=new Giudice(utenteCorrente.getNome(), utenteCorrente.getCognome(),
                utenteCorrente.getEmail(), utenteCorrente.getPassword(), utenteCorrente.getHackIDRichiesta() );

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
    hackathonDAOImplement.setUtenteToGiudiceDB(utenteCorrente.getID(), nuovoGiudice.getID(), utenteCorrente.getHackIDRichiesta());
    hackathonDAOImplement.storeNG(Giudice.getnG());

    piattaforma.getListaUtenReg().remove(utenteCorrente);
    piattaforma.getListaUtenReg().add(nuovoGiudice);
    findHackId( utenteCorrente.getHackIDRichiesta() ).addGiudice( nuovoGiudice );
    //NELLA GUI FAR RIACCEDERE L'UTENTE

    }

    public void rifiutaInvitoGiudice() throws SQLException{

        utenteCorrente.setHackIDRichiesta("");

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.eliminaRichiestaGiudice( utenteCorrente.getID() );
    }




    //DOPO LE ISCRIZIONI

    public void creaTeam(String nomeTeam,Hackathon hackathon)throws IllegalArgumentException,SQLException{

        hackathon.incrementaNpartec();//throws numero di partecipanti all'hackathon massimo
        Team team=utenteCorrente.creaTeam(piattaforma,hackathon,nomeTeam);//throws numero di membri massimo
        //IMPORTANTISSIMO L'ORDINE DI QUESTE 2 FUNZIONI

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.addTeamsDB(team.getID(),team.getNome(),team.getCodiceAccesso(),team.getIDHackathon());

        Partecipante partecipante=team.getArrayPartecipante().get(0);
        hackathonDAOImplement.setUtenteToPartecipanteDB(utenteCorrente.getID(), partecipante.getID(), partecipante.getIDHackathon(), partecipante.getIDTeam());
        hackathonDAOImplement.storeNP( Partecipante.getnP());
        hackathonDAOImplement.storeNT(Team.getnT());
    }

    public void addPartecToTeam(Team team,Hackathon hackathon) throws IllegalArgumentException,SQLException{

        Partecipante partecipante=null;

        if(hackathon.getNumeroPartec()<hackathon.N_MAX_PARTEC)
        {
            partecipante= utenteCorrente.registratiInTeam(piattaforma, hackathon,team);//throws "numero di membri al team massimo"

        }
        hackathon.incrementaNpartec(); //throws "numero di partecipanti all'hackathon massimo", presente ulteriore controllo

        //IMPORTANTISSIMO L'ORDINE DI QUESTE 2 FUNZIONI

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        hackathonDAOImplement.setUtenteToPartecipanteDB(utenteCorrente.getID(), partecipante.getID(),partecipante.getIDHackathon(), partecipante.getIDTeam());
        hackathonDAOImplement.storeNP( Partecipante.getnP());
        //inserimento nel DB del partecipante


    }

    /**
     * OPERAZIONE PARTECIPANTE
     */

    public void caricaProgresso(String nomeProgresso,String codiceProgresso,Team team) throws SQLException
    {
        Progresso progresso=team.caricaProgresso(nomeProgresso,codiceProgresso);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.addProgresso(progresso.getIdTeam(),progresso.getNome(),progresso.getCodiceProgresso());

    }

    public void setCommentoTeamCorrente(String commento, String nomeProgresso) throws SQLException{

        Progresso progresso=findProgressoTeamCorrente(nomeProgresso);
        if(progresso!=null){
            progresso.setCommento(commento);

            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
            hackathonDAOImplement.setCommentoProgresso(teamCorrente.getID(),nomeProgresso,commento);
        }
    }

    public Progresso findProgressoTeamCorrente(String nomeProgresso){

        for(Progresso progresso: teamCorrente.getArrayProgresso())
        {
            if(progresso.getNome().equals(nomeProgresso))
                return progresso;
        }
        return null;
    }




    /**
     * OPERAZIONI HACKATHON
     */


    public void getAllHackathonDB()throws SQLException{

        ArrayList<String> id=new ArrayList<>();
        ArrayList<String> nome=new ArrayList<>();
        ArrayList<Integer> n_max_partec=new ArrayList<>();
        ArrayList<Integer> n_team=new ArrayList<>();
        ArrayList<String> problema=new ArrayList<>();
        ArrayList<Boolean> team_suff=new ArrayList<>();
        ArrayList<Boolean> view_problema=new ArrayList<>();
        ArrayList<Boolean> evento_finito=new ArrayList<>();
        ArrayList<Boolean> votaz_conclusa=new ArrayList<>();

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.getAllHackathonDB(id,nome,n_max_partec,n_team,problema,team_suff,view_problema,evento_finito,votaz_conclusa);

        for(int i=0;i<id.size();i++){
            Hackathon hackathon=new Hackathon(id.get(i),nome.get(i),n_team.get(i), problema.get(i),team_suff.get(i),
                    view_problema.get(i), evento_finito.get(i), votaz_conclusa.get(i));
            piattaforma.getListaHackathon().add(hackathon);
        }




    }

    public void setHackathonCorrente(Hackathon hackathonCorrente) {this.hackathonCorrente = hackathonCorrente;}
    public Hackathon getHackathonCorrente() {return hackathonCorrente;}

    public Hackathon findHackId(String ID) throws IllegalArgumentException
    {
        for(Hackathon hackathon: piattaforma.getListaHackathon())
        {
            if(hackathon.getID().equals(ID))
                return hackathon;
        }
        throw new IllegalArgumentException("ID non presente");
    }


    public void removeHackathonNoGiudici() throws  SQLException{

        Hackathon hackathon;
        for(int i=0;i<getListaHackathon().size();)
        {
            hackathon=getListaHackathon().get(i);
            if(hackathon.getListaGiudici().isEmpty())
                removeHackathon(hackathon);
            else
                i++;


        }
    }

/**
 * OPERAZIONI AMMINISTRATORE
 */

    public void addHackathon(String nomeHackathon) throws SQLException
    {
        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        Hackathon hackathon;
        if(utenteCorrente instanceof Organizzatore organizzatore) {
            hackathon = organizzatore.creaHackathon(piattaforma, nomeHackathon);
            hackathonDAOImplement.storeNH(Hackathon.getnH());
            hackathonDAOImplement.addHackathonDB(hackathon.getID(),hackathon.getNome());


        }

    }

    public void setEventoFinito(boolean isEventoFinito) throws SQLException{

        hackathonCorrente.setEventoFinito(isEventoFinito);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setEventoFinito(true, hackathonCorrente.getID());
    }




    public void removeHackathon(Hackathon hackathon)throws SQLException{

        Giudice  giudice;
        if(!hackathon.getListaGiudici().isEmpty())
        {
            for(int i=0;i<hackathon.getListaGiudici().size();)
            {
                giudice=hackathon.getListaGiudici().get(i);
                System.out.println(giudice.getNome()+giudice.getCognome()+giudice.getID());
                removeGiudice(giudice,hackathon);
                }
        }

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        piattaforma.getListaHackathon().remove(hackathon);
        hackathonDAOImplement.removeHackathonDB(hackathon.getID());


    }

    //Selezione giudice senza richiesta
    public void addGiudiceHackaton(Hackathon hackathon,String utenteID) throws IllegalArgumentException{

        Utente_registrato utente= trovaUtenteForGiudice(hackathon,utenteID);
            if(utenteCorrente instanceof Organizzatore organizzatore)
                organizzatore.SelezionaGiudice( utente, piattaforma, hackathon);


    }

    public void mandaRichiestaUtenteForGiudice(Utente_registrato utente, Hackathon hackathon) throws SQLException{

        String richiesta="Vuoi essere giudice dell'hackathon:\n"+hackathon.getNome();
        utente.setHackIDRichiesta(hackathon.getID());
        utente.setRichiestaGiudice(richiesta);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.salvaRichiestaDB(utente.getID(), hackathon.getID(), richiesta);
    }

    public Utente_registrato trovaUtenteForGiudice(Hackathon hackathon, String utenteID)throws IllegalArgumentException{

        if(utenteID.equals("0000"))
            throw new IllegalArgumentException("L'amministratore non può essere un giudice");

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getID().equals(utenteID)){

                if(utente instanceof Giudice)
                {throw new IllegalArgumentException("L'utente scelto è già giudice");}

                else
                {
                    if(utente.getRichiestaGiudice().isEmpty()){

                        return utente;
                    }

                    else
                        throw new IllegalArgumentException("L'utente ha già una richiesta per essere giudice");
                }

            }
        }
        throw new IllegalArgumentException("ID Utente non trovato");
    }



    public void removeGiudice(Giudice giudice,Hackathon hackathon)throws SQLException{
        Utente_registrato utenteFromGiudice;
        if(utenteCorrente instanceof Organizzatore organizzatore){
          utenteFromGiudice=organizzatore.rimuoviGiudice(giudice,piattaforma,hackathon);

            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
            hackathonDAOImplement.setGiudiceToUtenteDB(utenteFromGiudice.getID(),giudice.getID());
            hackathonDAOImplement.storeNU(Utente_registrato.getnU());
        }
    }

    public void apriIscrizioni() throws SQLException{
        if(utenteCorrente instanceof Organizzatore organizzatore){
            organizzatore.apriIscrizioni(this.piattaforma);
            removeHackathonNoGiudici();

            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
            hackathonDAOImplement.setOpenIscr(true);

        }

    }

    public void chiudiIscrizioni()throws SQLException{

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        if(utenteCorrente instanceof Organizzatore organizzatore){

            organizzatore.chiudiIscrizioni(this.piattaforma);
            piattaforma.setEventoPronto(true);

            hackathonDAOImplement.setOpenIscr(false);
            hackathonDAOImplement.setEventoPronto(true);


        }


        for(Hackathon hackathon: piattaforma.getListaHackathon()){
            hackathon.fareHackathonFromNTeams();
            hackathonDAOImplement.setTeamSuff( hackathon.isTeam_suffic(), hackathon.getID() );
        }

    }

    public Giudice findIdGiudice(Hackathon hackathon,String idGiudice)throws IllegalArgumentException{
        for(Giudice giudice: hackathon.getListaGiudici())
        {
            if(giudice.getID().equals(idGiudice)){

                {
                    return giudice;
                }

            }
        }
        throw new IllegalArgumentException("ID Giudice non trovato");
    }

    /**
     * OPERAZIONI GIUDICE
     */

    public void assegnaVotoTeam(Team team,String votoString)throws IllegalArgumentException,SQLException{

        if(utenteCorrente instanceof Giudice giudice)
        {
            giudice.ValutaTeam(team,votoString);

            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
            hackathonDAOImplement.setVotoTeam(team.getID(), Float.parseFloat(votoString));
        }
    }


    public void salvaProblema(String problema) throws SQLException{
        getHackathonCorrente().setProblema(problema);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setHackathonProblema(hackathonCorrente.getID(), problema);
        //DB Set problema

    }

    public void pubblicaProblema()throws SQLException{

        getHackathonCorrente().setView_problema(true);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setViewProblema(true, hackathonCorrente.getID());

        //DB Flag view problema
    }


    public void concludiVotazioni()throws SQLException{

        hackathonCorrente.setVotazioneConclusa(true);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setVotazConclusa(true, hackathonCorrente.getID());
    }
















    public boolean isEventoPronto() {return piattaforma.isEventoPronto();}
    public boolean isOpenIscri(){return piattaforma.isOpen_iscr();}
    public boolean isEventoFinito(Hackathon hackathon){return hackathon.isEventoFinito();}


    public ArrayList<Utente_registrato> getListaUtenti(){return piattaforma.getListaUtenReg();}
    public ArrayList<Giudice> getListaGiudici(String IDHack){
        for(Hackathon hackathon: piattaforma.getListaHackathon())
        {
            if(hackathon.getID().equals(IDHack))
                return hackathon.getListaGiudici();
        }
        return null;
    }
    public ArrayList<Hackathon> getListaHackathon(){return piattaforma.getListaHackathon();}
    public Utente_registrato getAmministratore() {return amministratore;}
    public Piattaforma getPiattaforma() {return piattaforma;}
}
