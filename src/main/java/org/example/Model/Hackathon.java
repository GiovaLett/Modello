package org.example.Model;

import org.example.Model.ruoli.Team;

import java.util.ArrayList;

public class Hackathon
{
    public final int n_max_partec=500;
    public final int dim_max_team =5;
    int n_partec=0;
    ArrayList<Team> ListaTeam =new ArrayList<>();

    public String nome;
    public int n_team;

    public boolean team_suffic =false;

    public String sede;
    public class Data {
        public int anno;
        public int mese;
        public int giorno;
    }
    public Data data;

    int durata=3;     //giorni

    String problema="Descrizione Problema";


    Hackathon(){ this.data=new Data();} //Necessario il costruttore di data, altrimenti darà errore, PointerNull riferito a data

    public ArrayList<Team> getListaTeam() {return ListaTeam;}
    public int getNumeroPartec() {return n_partec;}


    //Se è presente un solo team l'hackaton non si fa
    public void FareHackathon(){        //In base al numero dei team che si sono creati
        if(n_team<2)
            team_suffic =false;
        else team_suffic =true;
    }
}
