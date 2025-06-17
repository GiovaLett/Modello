package org.example.Model.ruoli;

import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;

public class Utente_registrato extends Utente {

    private static int nU =0;
    protected String email;
    protected String password;
    protected String ID;

    private String richiestaGiudice="";
    private String hackIDRichiesta;

    public Utente_registrato(String nome, String cognome, String email, String password) {
        super(nome, cognome);
        this.email = email;
        this.password = password;
        this.ID = Codice_ID();
    }
    public Utente_registrato() {}

    public String getEmail() {return email;}
    public String getPassword(){return password;}

    public String getID() {return ID;}


    private String Codice_ID(){
        String ID_codice="-1";
        if(nU >=0 && nU <10)  ID_codice="U00"+String.valueOf(nU);

        else if (nU <100)   ID_codice="U0"+String.valueOf(nU);

        else if (nU <1000)   ID_codice="U"+String.valueOf(nU);

        nU++;

        return ID_codice;
    }

    //IMPORTANTISSIMO L'ORDINE DELLE FUNZIONI CON QUELLA CHE LANCIA UN'ECCEZIONE
    public void registratiInTeam(Piattaforma piattaforma,Hackathon hackathon, Team team) throws IllegalArgumentException
    {
        Partecipante nuovoPartec=new Partecipante(this.getNome(), this.getCognome(), this.getEmail(), this.getPassword(), team.getID(), hackathon.getID());
        team.addPartecipante(nuovoPartec);//throws
        piattaforma.getListaUtenReg().add(nuovoPartec);
        piattaforma.getListaUtenReg().remove(this);
    }

    public void creaTeam(Piattaforma piattaforma, Hackathon hackathon, String nomeTeam) throws IllegalArgumentException{
        Team nuovoTeam=new Team(nomeTeam,hackathon);
        registratiInTeam(piattaforma,hackathon,nuovoTeam);//throws
        hackathon.addTeam(nuovoTeam);

    }

    public void setRichiestaGiudice(String richiestaGiudice) {this.richiestaGiudice = richiestaGiudice;}
    public String getRichiestaGiudice() {return richiestaGiudice;}

    public void setHackIDRichiesta(String hackIDRichiesta) {this.hackIDRichiesta = hackIDRichiesta;}
    public String getHackIDRichiesta() {return hackIDRichiesta;}
}