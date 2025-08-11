package org.example.model.ruoli;

import org.example.model.Hackathon;
import org.example.model.Piattaforma;

public class UtenteRegistrato extends Utente {

    private static int nU =0;
    protected String email;
    protected String password;
    protected String id;

    private String richiestaGiudice="";
    private String hackIDRichiesta;

    public UtenteRegistrato(String nome, String cognome, String email, String password) {
        super(nome, cognome);
        this.email = email;
        this.password = password;
        this.id = codiceID();
    }

    public UtenteRegistrato(String id, String nome, String cognome, String email, String password){

        this.id = id;   this.nome=nome;   this.cognome=cognome;   this.email=email;   this.password=password;
    }

    public UtenteRegistrato() {}

    public String getEmail() {return email;}
    public String getPassword(){return password;}

    public String getId() {return id;}


    private String codiceID(){
        String idCodice ="-1";
        if(nU >=0 && nU <10)  idCodice ="U00"+nU;

        else if (nU <100)   idCodice ="U0"+nU;

        else if (nU <1000)   idCodice ="U"+nU;

        nU++;

        return idCodice;
    }

    public static int getnU() {return nU;}
    public static void setnU(int nU) {
        UtenteRegistrato.nU = nU;}

    //IMPORTANTISSIMO L'ORDINE DELLE FUNZIONI CON QUELLA CHE LANCIA UN'ECCEZIONE
    public Partecipante registratiInTeam(Piattaforma piattaforma,Hackathon hackathon, Team team) throws IllegalArgumentException
    {
        Partecipante nuovoPartec=new Partecipante(this.getNome(), this.getCognome(), this.getEmail(), this.getPassword(), team.getId(), hackathon.getId());
        team.addPartecipante(nuovoPartec);//throws
        piattaforma.getListaUtenReg().add(nuovoPartec);
        piattaforma.getListaUtenReg().remove(this);
        return nuovoPartec;
    }

    public Team creaTeam(Piattaforma piattaforma, Hackathon hackathon, String nomeTeam) throws IllegalArgumentException{

        Team nuovoTeam=new Team(nomeTeam,hackathon);
        registratiInTeam(piattaforma,hackathon,nuovoTeam);//throws
        hackathon.addTeam(nuovoTeam);
        return nuovoTeam;

    }

    public void setRichiestaGiudice(String richiestaGiudice) {this.richiestaGiudice = richiestaGiudice;}
    public String getRichiestaGiudice() {return richiestaGiudice;}

    public void setHackIDRichiesta(String hackIDRichiesta) {this.hackIDRichiesta = hackIDRichiesta;}
    public String getHackIDRichiesta() {return hackIDRichiesta;}
}