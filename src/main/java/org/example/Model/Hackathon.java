package org.example.Model;

public class Hackathon
{
    public String sede;
    public String nome;

    public class Data {
        public int anno;
        public int mese;
        public int giorno;
    }
     public Data data;

    Hackathon(){ this.data=new Data();} //Necessario il costruttore di data, altrimenti darà errore, PointerNull riferito a data

    public final int n_max_partec=500;
    public final int dim_max_team =5;


    int durata=3;//giorni
    public int n_partec=0;




}
