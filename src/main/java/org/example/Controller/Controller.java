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
    public Team findTeam(String idTeam) throws IllegalArgumentException{
        Team teamTrovato;
        for(Hackathon hackathon: piattaforma.getListaHackathon()) {
            teamTrovato = findIDTeam(idTeam, hackathon);

            if (teamTrovato != null) {
                return teamTrovato;
            }
        }
        throw new IllegalArgumentException("ID Team del partecipante non trovato");
    }

    public Team findIDTeam(String ID,Hackathon hackathon)
    {
        for(Team team: hackathon.getListaTeam())
        {
            if(team.getID().equals(ID)) return team;
        }
        return null;
    }

    public Team findCodeAccessTeam(String codeAccess,Hackathon hackathon) throws IllegalArgumentException
    {
        for(Team team: hackathon.getListaTeam())
        {
            if(team.getCodiceAccesso().equals(codeAccess)) return team;
        }

        throw new IllegalArgumentException("Codice di accesso errato");
    }

    /**
     *  OPERAZIONE UTENTE REGISTRATO
     */

    public void creaTeam(String nomeTeam,Hackathon hackathon)throws IllegalArgumentException{

        hackathon.incrementaNpartec();//throws numero di partecipanti all'hackathon massimo
        utenteCorrente.creaTeam(piattaforma,hackathon,nomeTeam);//throws numero di membri massimo
        //IMPORTANTISSIMO L'ORDINE DI QUESTE 2 FUNZIONI
    }

    public void addPartecToTeam(Team team,Hackathon hackathon) throws IllegalArgumentException{

        if(hackathon.getNumeroPartec()<hackathon.n_max_partec)
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


    public void addGiudiceHackaton(Hackathon hackathon,String utenteID) throws IllegalArgumentException{


        if(utenteID.equals("0000"))
            throw new IllegalArgumentException("L'amministratore non può essere un giudice");

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getID().equals(utenteID)){

                if(utente instanceof Giudice)
                {throw new IllegalArgumentException("L'utente scelto è già giudice");}

                else
                {
                    if(utenteCorrente instanceof Organizzatore organizzatore)
                     organizzatore.SelezionaGiudice( utente, piattaforma, hackathon);
                    return;
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








    public boolean isEventoPronto() {return piattaforma.isEventoPronto();}

    public boolean isOpenIscri(){return piattaforma.isOpen_iscr();}


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
