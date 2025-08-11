package org.example.model.ruoli;

public class Partecipante extends UtenteRegistrato {

    private static int nP =0;  //Per la creazione di un codice ID del team nel caso venga creato

    private final String idTeam;
    private final String idHackathon;

    public Partecipante(String nome,String cognome,String email,String password,String idTeam,String idHackathon){
        super(nome,cognome,email,password);
        this.id = creaIDPartecip();
        this.idTeam = idTeam;
        this.idHackathon = idHackathon;

    }

    public Partecipante(String id, String nome, String cognome, String email, String password, String idTeam, String idHackathon){
        super(nome,cognome,email,password);
        this.id = id;   this.idTeam = idTeam;   this.idHackathon = idHackathon;

    }



    public static int getnP() { return nP;}
    public static void setnP(int nP) {Partecipante.nP = nP;}

    private String creaIDPartecip(){

        String idCodice ="-1";
        if(nP >=0 && nP <10)  idCodice ="P00"+nP;

        else if (nP <100)   idCodice ="P0"+nP;

        else if (nP <1000)   idCodice ="P"+nP;

        nP++;

        return idCodice;

    }



    public String getIdTeam() {return idTeam;}

    public String getIdHackathon() {return idHackathon;}
}