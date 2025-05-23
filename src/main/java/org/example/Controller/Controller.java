package org.example.Controller;

import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;
import org.example.Model.ruoli.*;

import java.util.ArrayList;

public class Controller
{
    Utente_registrato amministratore=new Organizzatore("0000","Giovanni","Lettieri","gio@email.it","password");

    Piattaforma piattaforma=new Piattaforma();



    public Controller(){

        piattaforma.addUtenteReg(amministratore);
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
        piattaforma.addUtenteReg(new Utente_registrato("Italo","Calvino","italo@email.it","password","U000"));
    }


    public void addGiudiceHackaton(Hackathon hackathon,String utenteID) throws IllegalArgumentException{


        if(utenteID.equals("0000"))
            throw new IllegalArgumentException("L'amministratore non può essere un giudice");

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
                    if(utente.getID().equals(utenteID)){

                        if(utente instanceof Giudice)
                        {throw new IllegalArgumentException("L'utente scelto è già giudice");}

                        else {
                            Giudice nuovoGiudice = new Giudice(utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getPassword(), hackathon.getID());
                            hackathon.addGiudice(nuovoGiudice);
                            piattaforma.getListaUtenReg().remove(utente);
                            piattaforma.getListaUtenReg().add(nuovoGiudice);

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
