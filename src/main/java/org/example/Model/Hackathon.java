package org.example.Model;

import org.example.Model.ruoli.*;


import java.util.ArrayList;

public class Hackathon
{
    private static int n=0;


    public final int n_max_partec=500;
    public final int dim_max_team =5;
    int n_partec=0;
    ArrayList<Team> ListaTeam =new ArrayList<>();
    ArrayList<Giudice> ListaGiudici=new ArrayList<>();

    String ID;
     String nome;


    int n_team;
    public boolean team_suffic =false;

    public String sede;
    public class Data {
        public int anno;
        public int mese;
        public int giorno;
    }
    public Data data;

    int durata=3;     //giorni
    private boolean view_problema =false;// indica se l'Hackaton verrà fatto in base al numero dei team
    String problema="Descrizione Problema";


    Hackathon(){ this.data=new Data(); ID=Codice_ID();} //Necessario il costruttore di data, altrimenti darà errore, PointerNull riferito a data

    public ArrayList<Team> getListaTeam() {return ListaTeam;}
    public ArrayList<Giudice> getListaGiudici(){return ListaGiudici;}

    public int getNumeroPartec() {return n_partec;}
    public String getID() {return ID;}
    public String getNome(){return nome;}
    public int getN_partec(){return n_partec;}
    public void incrementaNpartec(){n_partec++;}

    void setNome(String nome){this.nome=nome;}

    //Se è presente un solo team l'hackaton non si fa
    public void FareHackathon(){        //In base al numero dei team che si sono creati
        if(n_team<2)
            team_suffic =false;
        else team_suffic =true;
    }


    public void addGiudice(Giudice giudice){ListaGiudici.add(giudice);}
    public void addTeam(Team team){ListaTeam.add(team);}

    public boolean isView_problema() {return view_problema;}
    public void setView_problema(boolean view_problema) {this.view_problema = view_problema;}

    public boolean isTeam_suffic() {return team_suffic;}

    private String Codice_ID(){
        String ID_codice="-1";
        if(n>=0 && n<10)  ID_codice="H00"+String.valueOf(n);

        else if (n<100)   ID_codice="H0"+String.valueOf(n);

        else if (n<1000)   ID_codice="H"+String.valueOf(n);

        n++;

        return ID_codice;
    }
}
