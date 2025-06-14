package org.example.Model.ruoli;

import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;

public class Utente_registrato extends Utente {

    private static int nu=0;
    protected String email;
    protected String password;
    protected String ID;

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
        if(nu>=0 && nu<10)  ID_codice="U00"+String.valueOf(nu);

        else if (nu<100)   ID_codice="U0"+String.valueOf(nu);

        else if (nu<1000)   ID_codice="U"+String.valueOf(nu);

        nu++;

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
}