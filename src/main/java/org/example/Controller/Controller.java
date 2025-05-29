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

    public Utente_registrato getUtenteCorrente() {return utenteCorrente;}

    public void setUtenteCorrente(Utente_registrato utenteCorrente) {
        this.utenteCorrente = utenteCorrente;
    }


    public Utente_registrato findAccount(String email, String password) throws IllegalArgumentException{

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getEmail().equals(email) && utente.getPassword().equals(password))
                return utente;
        }
        throw new IllegalArgumentException("Credenziali errate");
    }

    public Hackathon findHackId(String ID) throws IllegalArgumentException
    {
        for(Hackathon hackathon: piattaforma.getListaHackathon())
        {
            if(hackathon.getID().equals(ID))
                return hackathon;
        }
        throw new IllegalArgumentException("ID non presente");
    }



    public void addUtenteReg(){
        piattaforma.addUtenteReg(new Utente_registrato("Italo","Calvino","italo@email.it","password"));
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

    public void creaTeam(String nomeTeam,Hackathon hackathon){
        utenteCorrente.creaTeam(piattaforma,hackathon,nomeTeam);
    }

    public void addPartecToTeam(Team team,Hackathon hackathon) throws IllegalArgumentException{

        utenteCorrente.registratiInTeam(piattaforma, hackathon,team);

    }





    public boolean isOpenIscri(){return piattaforma.isOpen_iscr();}


    public void apriIscrizioni(){
        if(utenteCorrente instanceof Organizzatore organizzatore)
            organizzatore.apriIscrizioni(this.piattaforma);
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
