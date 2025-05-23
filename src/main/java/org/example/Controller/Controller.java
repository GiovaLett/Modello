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
        piattaforma.addUtenteReg(new Utente_registrato("giovanni","lettieri","gio@email.it","password","ID"));
    }

    public ArrayList<Utente_registrato> getListaUtenti(){return piattaforma.getListaUtenReg();}
    public ArrayList<Hackathon> getListaHackathon(){return piattaforma.getListaHackathon();}

    public Utente_registrato getAmministratore() {return amministratore;}

    public Piattaforma getPiattaforma() {return piattaforma;}
}
