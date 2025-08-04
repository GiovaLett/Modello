package org.example.Model;

import org.example.Model.ruoli.*;


import java.util.ArrayList;

public class Hackathon
{


    private String nome;
    private String ID;
    private static int nH =0;//Per la creazione dei codiciID

    public final int N_MAX_PARTEC =50;
    private int n_partec=0;

    private int n_team;

    private int durata;
    private String sede;

    private String problema="";
    ArrayList<Team> ListaTeam =new ArrayList<>();
    ArrayList<Giudice> ListaGiudici=new ArrayList<>();


    //Ordine temporale di attivazione
    private boolean team_suffic =false;
    private boolean view_problema =false;
    private boolean eventoFinito=false;
    private boolean votazioneConclusa =false;
    private boolean classificaPubblicata=false;



    public Hackathon(){ ID=Codice_ID();}


    public Hackathon(String nome){this.nome=nome;  ID=Codice_ID();}


   public  Hackathon(String id,String nome,int n_team,String problema,
              boolean team_suff,boolean view_problema,boolean evento_finito,boolean votaz_conclusa, boolean classificaPubblicata,String sede,int durata){

        this.ID=id;  this.nome=nome;   this.n_team=n_team;   this.problema=problema;

        this.team_suffic=team_suff;  this.view_problema=view_problema;  this.eventoFinito=evento_finito;

        this.votazioneConclusa=votaz_conclusa;  this.classificaPubblicata=classificaPubblicata;
        this.sede=sede;     this.durata=durata;

    }

    public void setID(String ID) {this.ID = ID;}
    public String getID() {return ID;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome=nome;}

    public void setDurata(int durata) {this.durata = durata;}
    public int getDurata() {return durata;}

    public void setSede(String sede) {this.sede = sede;}
    public String getSede() {return sede;}

    public int getN_team() {
        return n_team;
    }

    public void setN_team(int n_team) {
        this.n_team = n_team;
    }

    public ArrayList<Team> getListaTeam() {return ListaTeam;}
    public ArrayList<Giudice> getListaGiudici(){return ListaGiudici;}

    public int getNumeroPartec() {return n_partec;}
    public void incrementaNpartec() throws IllegalArgumentException //Se n_partec==n_max
    {
        if(n_partec< N_MAX_PARTEC)
            n_partec++;
        else
            throw new IllegalArgumentException("Numero di partecipanti massimo raggiunto per questo hackathon\n" +
                    "Impossibile iscriversi");

    }



    //Se è presente un solo team l'hackaton non si fa
    public void fareHackathonFromNTeams(){        //In base al numero dei team che si sono creati
        if(n_team<2)
            team_suffic =false;
        else team_suffic =true;
    }

    public void setProblema(String problema) {this.problema = problema;}
    public String getProblema() {return problema;}

    public void addGiudice(Giudice giudice){ListaGiudici.add(giudice);}
    public void addTeam(Team team){ListaTeam.add(team);n_team++;}

    public boolean isView_problema() {return view_problema;}
    public void setView_problema(boolean view_problema) {this.view_problema = view_problema;}

    public boolean isVotazioneConclusa() {return votazioneConclusa;}
    public void setVotazioneConclusa(boolean votazioneConclusa) {
        this.votazioneConclusa = votazioneConclusa;}

    public boolean isEventoFinito(){return eventoFinito;}
    public void setEventoFinito(boolean isEventoFinito){this.eventoFinito=isEventoFinito;}

    public void setClassificaPubblicata(boolean classificaPubblicata) {this.classificaPubblicata = classificaPubblicata;}
    public boolean isClassificaPubblicata() {return classificaPubblicata;}

    public boolean isTeam_suffic() {return team_suffic;}

    private String Codice_ID(){
        String ID_codice="-1";
        if(nH >=0 && nH <10)  ID_codice="H00"+String.valueOf(nH);

        else if (nH <100)   ID_codice="H0"+String.valueOf(nH);

        else if (nH <1000)   ID_codice="H"+String.valueOf(nH);

        nH++;

        return ID_codice;
    }

    public static int getnH() {return nH;}

    public static void setnH(int nH) {
        Hackathon.nH = nH;
    }
}
