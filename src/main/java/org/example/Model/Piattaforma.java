package org.example.Model;
import org.example.Model.ruoli.*;

import java.util.ArrayList;

public class Piattaforma
{
    private boolean open_iscr =false;// indica la possibilità di iscriversi
    private boolean view_problema =false;// indica se l'Hackaton verrà fatto in base al numero dei team


    ArrayList<Utente_registrato> ListaUtenReg =new ArrayList<>();
    ArrayList<Hackathon> ListaHackathon=new ArrayList<>();


    public boolean isOpen_iscr() {return open_iscr;}
    public void setOpen_iscr(boolean open_iscr) {this.open_iscr = open_iscr;}

    public boolean isView_problema() {return view_problema;}
    public void setView_problema(boolean view_problema) {this.view_problema = view_problema;}

    public ArrayList<Hackathon> getListaHackathon() {return ListaHackathon;}
    public ArrayList<Utente_registrato> getListaUtenReg() {return ListaUtenReg;}


    public void addUtenteReg(Utente_registrato nuovoUtente){ListaUtenReg.add(nuovoUtente);}


    public void addHackathon(Hackathon hackathon){ListaHackathon.add(hackathon);}

    public void buildListaHackathon(int n)
    {
        for(int i=0; i<n ; i++)
        {
            Hackathon hackathon=new Hackathon();
            hackathon.setNome("Hackathon"+i);
            ListaHackathon.add(hackathon);
        }
    }












/*

    private class Classificato {
        String nome;
        float voto;

        public Classificato(String nome, float voto){
            this.voto=voto;
            this.nome=nome;
        }
    }

    ArrayList<Classificato> Classifica=new ArrayList<>();
    public ArrayList<Classificato> getClassifica() {return Classifica;}
    //  CREA CLASSIFICA

    public void CreaClassifica(){
        float min;

        int k;
        for(int i = 0; i<this.ListaTeam.size(); i++){

            min=this.ListaTeam.get(0).Voto;
            k=i;

            for(int j = i; j<this.ListaTeam.size()-i; j++)
            {
                if( this.ListaTeam.get(j).Voto<min)
                {
                    min=this.ListaTeam.get(j).Voto;
                    k=j;
                }

            }
            ListaTeam.add(ListaTeam.get(k));
            Classificato classificato=new Classificato(ListaTeam.get(k).Nome, ListaTeam.get(k).Voto);
            Classifica.add(0,classificato);
            ListaTeam.remove(k);

        }

    }



      STAMPA CLASSIFICA

    public void StampaClassifica(){

        System.out.println("CLASSIFICA:\n");
        int i=1;
        for(Classificato classificato:Classifica)
        {
            System.out.println(i+".  "+classificato.nome+" ("+ classificato.voto+")");
        }
    }


      MOSTRA PROBLEMA

    public String getProblema()
    {
        if(this.view_problema)
            return this.problema;
        else
            return "Problema non disponibile";

    }*/

}

