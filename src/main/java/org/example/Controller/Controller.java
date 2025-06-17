package org.example.Controller;

import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;
import org.example.Model.ruoli.*;

import java.util.ArrayList;

public class Controller
{
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

    public void addUtenteRegistrato(String nome,String cognome,String email,String password)
    {
        piattaforma.addUtenteReg(new Utente_registrato(nome,cognome,email,password));
    }


    public Utente_registrato findAccount(String email, String password) throws IllegalArgumentException{

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getEmail().equals(email) && utente.getPassword().equals(password))
                return utente;
        }
        throw new IllegalArgumentException("Credenziali errate");
    }




    /**
 * OPERAZIONI SUI TEAM
 */
    public void setTeamCorrente(Team teamCorrente) {    this.teamCorrente = teamCorrente;}
    public Team getTeamCorrente() {return teamCorrente;}

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

    /**
     *  OPERAZIONE UTENTE REGISTRATO
     */


    //PRIMA DELLE ISCRIZIONI
    public void diventaGiudice(){

        Giudice nuovoGiudice=new Giudice(utenteCorrente.getNome(), utenteCorrente.getCognome(),
                utenteCorrente.getEmail(), utenteCorrente.getPassword(), utenteCorrente.getHackIDRichiesta() );
    piattaforma.getListaUtenReg().remove(utenteCorrente);
    piattaforma.getListaUtenReg().add(nuovoGiudice);


    findHackId( utenteCorrente.getHackIDRichiesta() ).addGiudice( nuovoGiudice );
    //NELLA GUI FAR RIACCEDERE L'UTENTE

    }




    //DOPO LE ISCRIZIONI

    public void creaTeam(String nomeTeam,Hackathon hackathon)throws IllegalArgumentException{

        hackathon.incrementaNpartec();//throws numero di partecipanti all'hackathon massimo
        utenteCorrente.creaTeam(piattaforma,hackathon,nomeTeam);//throws numero di membri massimo
        //IMPORTANTISSIMO L'ORDINE DI QUESTE 2 FUNZIONI
    }

    public void addPartecToTeam(Team team,Hackathon hackathon) throws IllegalArgumentException{

        if(hackathon.getNumeroPartec()<hackathon.N_MAX_PARTEC)
        {
            utenteCorrente.registratiInTeam(piattaforma, hackathon,team);//throws "numero di membri al team massimo"

        }
        hackathon.incrementaNpartec(); //throws "numero di partecipanti all'hackathon massimo", presente ulteriore controllo

        //IMPORTANTISSIMO L'ORDINE DI QUESTE 2 FUNZIONI

    }

    /**
     * OPERAZIONE PARTECIPANTE
     */

    public void caricaProgresso(String nomeProgresso,String progresso,Team team)
    {
        team.caricaProgresso(nomeProgresso,progresso);
    }




    /**
     * OPERAZIONI HACKATHON
     */

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


    public void removeHackathonNoGiudici(){

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

    public void addHackathon(String nomeHackathon)
    {
        if(utenteCorrente instanceof Organizzatore organizzatore)
            organizzatore.creaHackathon(piattaforma,nomeHackathon);
    }

    public void removeHackathon(Hackathon hackathon){

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
        piattaforma.getListaHackathon().remove(hackathon);

    }

    //Selezione giudice senza richiesta
    public void addGiudiceHackaton(Hackathon hackathon,String utenteID) throws IllegalArgumentException{

        Utente_registrato utente= trovaUtenteForGiudice(hackathon,utenteID);
            if(utenteCorrente instanceof Organizzatore organizzatore)
                organizzatore.SelezionaGiudice( utente, piattaforma, hackathon);


    }

    public void mandaRichiestaUtenteForGiudice(Utente_registrato utente, Hackathon hackathon) {

        utente.setHackIDRichiesta(hackathon.getID());
        utente.setRichiestaGiudice("Vuoi essere giudice dell'hackathon:\n"+hackathon.getNome());
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



    public void removeGiudice(Giudice giudice,Hackathon hackathon){
        if(utenteCorrente instanceof Organizzatore organizzatore)
            organizzatore.rimuoviGiudice(giudice,piattaforma,hackathon);
    }

    public void apriIscrizioni(){
        if(utenteCorrente instanceof Organizzatore organizzatore)
            organizzatore.apriIscrizioni(this.piattaforma);
        removeHackathonNoGiudici();
    }

    public void chiudiIscrizioni(){
        if(utenteCorrente instanceof Organizzatore organizzatore){
            organizzatore.chiudiIscrizioni(this.piattaforma);

            piattaforma.setEventoPronto(true);

        }


        for(Hackathon hackathon: piattaforma.getListaHackathon())
            hackathon.fareHackathonFromNTeams();
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

    public void assegnaVotoTeam(Team team,String votoString)throws IllegalArgumentException{

        if(utenteCorrente instanceof Giudice giudice)
        {
            giudice.ValutaTeam(team,votoString);
        }
    }

    //public void pubblicaProblema()














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
