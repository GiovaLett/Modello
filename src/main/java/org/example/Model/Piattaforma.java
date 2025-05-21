package org.example.Model;
import org.example.Model.ruoli.*;

import java.util.ArrayList;

public class Piattaforma
{
    public boolean open_iscr =false;// indica la possibilità di iscriversi
    public boolean view_problema =false;
    public boolean team_suffic =false;// indica se l'Hackaton verrà fatto in base al numero dei team
    public int n_team;

    private class Classificato {
        String nome;
        float voto;

        public Classificato(String nome, float voto){
            this.voto=voto;
            this.nome=nome;
        }
    }

    ArrayList<Classificato> Classifica=new ArrayList<>();
    public ArrayList<Utente_registrato> ArrayUtenReg =new ArrayList<>();
    public ArrayList<Team> ArrayTeam=new ArrayList<>();


    String problema="Descrizione Problema";


/**
 * CREA CLASSIFICA
 */
    public void CreaClassifica(){
        float min;

        int k;
        for(int i=0; i<this.ArrayTeam.size(); i++){

            min=this.ArrayTeam.get(0).Voto;
            k=i;

            for(int j=i; j<this.ArrayTeam.size()-i;j++)
            {
                if( this.ArrayTeam.get(j).Voto<min)
                {
                    min=this.ArrayTeam.get(j).Voto;
                    k=j;
                }

            }
            ArrayTeam.add(ArrayTeam.get(k));
            Classificato classificato=new Classificato(ArrayTeam.get(k).Nome,ArrayTeam.get(k).Voto);
            Classifica.add(0,classificato);
            ArrayTeam.remove(k);

        }

    }


    /**
     * STAMPA CLASSIFICA
     */
    public void StampaClassifica(){

        System.out.println("CLASSIFICA:\n");
        int i=1;
        for(Classificato classificato:Classifica)
        {
            System.out.println(i+".  "+classificato.nome+" ("+ classificato.voto+")");
        }
    }

    /**
     * MOSTRA PROBLEMA
     */
    public String getProblema()
    {
        if(this.view_problema)
            return this.problema;
        else
            return "Problema non disponibile";

    }


    //Se è presente un solo team l'hackaton non si fa
    public void FareHackathon(){        //In base al numero dei team che si sono creati
        if(n_team<2)
            team_suffic =false;
        else team_suffic =true;
    }






}

