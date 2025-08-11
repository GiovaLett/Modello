package org.example.model;

import org.example.model.ruoli.*;


import java.util.ArrayList;

public class Hackathon
{


    private String nome;
    private String id;
    private static int nH =0;//Per la creazione dei codiciID

    public static final int N_MAX_PARTEC =50;
    private int nPartec =0;

    private int nTeam;

    private int durata;
    private String sede;

    private String problema="";
    ArrayList<Team> listaTeam =new ArrayList<>();
    ArrayList<Giudice> listaGiudici =new ArrayList<>();


    //Ordine temporale di attivazione
    private boolean teamSuffic =false;
    private boolean viewProblema =false;
    private boolean eventoFinito=false;
    private boolean votazioneConclusa =false;
    private boolean classificaPubblicata=false;



    public Hackathon(){ id = codiceID();}


    public Hackathon(String nome){this.nome=nome;  id = codiceID();}


   public  Hackathon(String id, String nome, int nTeam, String problema,
                     boolean teamSuff, boolean viewProblema, boolean eventoFinito, boolean votazConclusa, boolean classificaPubblicata, String sede, int durata){

        this.id =id;  this.nome=nome;   this.nTeam = nTeam;   this.problema=problema;

        this.teamSuffic = teamSuff;  this.viewProblema = viewProblema;  this.eventoFinito= eventoFinito;

        this.votazioneConclusa= votazConclusa;  this.classificaPubblicata=classificaPubblicata;
        this.sede=sede;     this.durata=durata;

    }

    public void setId(String id) {this.id = id;}
    public String getId() {return id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome=nome;}

    public void setDurata(int durata) {this.durata = durata;}
    public int getDurata() {return durata;}

    public void setSede(String sede) {this.sede = sede;}
    public String getSede() {return sede;}



    public ArrayList<Team> getListaTeam() {return listaTeam;}
    public ArrayList<Giudice> getListaGiudici(){return listaGiudici;}

    public int getNumeroPartec() {return nPartec;}
    public void incrementaNpartec() throws IllegalArgumentException //Se n_partec==n_max
    {
        if(nPartec < N_MAX_PARTEC)
            nPartec++;
        else
            throw new IllegalArgumentException("Numero di partecipanti massimo raggiunto per questo hackathon\n" +
                    "Impossibile iscriversi");

    }



    //Se è presente un solo team l'hackaton non si fa
    public void fareHackathonFromNTeams(){        //In base al numero dei team che si sono creati
        if(nTeam <2)
            teamSuffic =false;
        else teamSuffic =true;
    }

    public void setProblema(String problema) {this.problema = problema;}
    public String getProblema() {return problema;}

    public void addGiudice(Giudice giudice){
        listaGiudici.add(giudice);}
    public void addTeam(Team team){
        listaTeam.add(team);
        nTeam++;}

    public boolean isViewProblema() {return viewProblema;}
    public void setViewProblema(boolean viewProblema) {this.viewProblema = viewProblema;}

    public boolean isVotazioneConclusa() {return votazioneConclusa;}
    public void setVotazioneConclusa(boolean votazioneConclusa) {
        this.votazioneConclusa = votazioneConclusa;}

    public boolean isEventoFinito(){return eventoFinito;}
    public void setEventoFinito(boolean isEventoFinito){this.eventoFinito=isEventoFinito;}

    public void setClassificaPubblicata(boolean classificaPubblicata) {this.classificaPubblicata = classificaPubblicata;}
    public boolean isClassificaPubblicata() {return classificaPubblicata;}

    public boolean isTeamSuffic() {return teamSuffic;}

    private String codiceID(){
        String idCodice ="-1";
        if(nH >=0 && nH <10)  idCodice ="H00"+nH;

        else if (nH <100)   idCodice ="H0"+nH;

        else if (nH <1000)   idCodice ="H"+nH;

        nH++;

        return idCodice;
    }

    public static int getnH() {return nH;}

    public static void setnH(int nH) {
        Hackathon.nH = nH;
    }
}
