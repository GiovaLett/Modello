package org.example.Controller;

import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;
import org.example.Model.ruoli.*;

import java.util.ArrayList;

public class Controller
{
    Piattaforma piattaforma=new Piattaforma();



    public Controller(){
        Utente_registrato amministratore=new Organizzatore();
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



    public void addUtenteReg(){
        piattaforma.addUtenteReg(new Utente_registrato("giovanni","lettieri","gio@email.it","password","ID"));
    }

    public ArrayList<Utente_registrato> getListaUtenti(){return piattaforma.getListaUtenReg();}
    public ArrayList<Hackathon> getListaHackathon(){return piattaforma.getListaHackathon();}
}
