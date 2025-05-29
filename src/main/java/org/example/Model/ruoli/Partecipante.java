package org.example.Model.ruoli;
import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;


import java.util.Scanner;

public class Partecipante extends Utente_registrato {

    private static int np =0;  //Per la creazione di un codice ID del team nel caso venga creato
    private boolean inAteam=false;
    private String IDTeam;


    public Partecipante(String nome,String cognome,String email,String password,String IDTeam){
        super(nome,cognome,email,password);
        this.ID=CreaIDPartecip();
        this.IDTeam=IDTeam;

    }





    private String CreaIDPartecip(){

        String ID_codice="-1";
        if(np >=0 && np <10)  ID_codice="P00"+String.valueOf(np);

        else if (np <100)   ID_codice="P0"+String.valueOf(np);

        else if (np <1000)   ID_codice="P"+String.valueOf(np);

        np++;

        return ID_codice;

    }


    public void setInAteam(boolean inAteam) {
        this.inAteam = inAteam;
    }

    public boolean getInAteam(){return this.inAteam;}

    public String getIDTeam() {return IDTeam;}
}