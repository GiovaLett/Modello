package org.example.Model.ruoli;

public class Partecipante extends Utente_registrato {

    private static int nP =0;  //Per la creazione di un codice ID del team nel caso venga creato

    private String IDTeam;
    private String IDHackathon;

    public Partecipante(String nome,String cognome,String email,String password,String IDTeam,String IDHackathon){
        super(nome,cognome,email,password);
        this.ID=CreaIDPartecip();
        this.IDTeam=IDTeam;
        this.IDHackathon=IDHackathon;

    }

    public Partecipante(String ID, String nome,String cognome,String email,String password,String IDTeam,String IDHackathon){
        super(nome,cognome,email,password);
        this.ID=ID;   this.IDTeam=IDTeam;   this.IDHackathon=IDHackathon;

    }



    public static int getnP() { return nP;}
    public static void setnP(int nP) {Partecipante.nP = nP;}

    private String CreaIDPartecip(){

        String ID_codice="-1";
        if(nP >=0 && nP <10)  ID_codice="P00"+String.valueOf(nP);

        else if (nP <100)   ID_codice="P0"+String.valueOf(nP);

        else if (nP <1000)   ID_codice="P"+String.valueOf(nP);

        nP++;

        return ID_codice;

    }



    public String getIDTeam() {return IDTeam;}

    public String getIDHackathon() {return IDHackathon;}
}