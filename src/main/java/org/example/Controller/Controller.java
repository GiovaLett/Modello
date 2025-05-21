package org.example.Controller;

import org.example.Model.ruoli.*;

import java.util.ArrayList;

public class Controller
{
    ArrayList<Utente_registrato>registroUtenti=new ArrayList<>();



    public Utente_registrato findAccount(String email, String password) throws IllegalArgumentException{

        for(Utente_registrato utente:registroUtenti)
        {
            if(utente.getEmail().equals(email) && utente.getPassword().equals(password))
                return utente;
        }
        throw new IllegalArgumentException("Credenziali errate");
    }



    public void addUtenteReg(){
        registroUtenti.add(new Utente_registrato("giovanni","lettieri","gio@email.it","password","ID"));
    }
}
