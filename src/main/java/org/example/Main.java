package org.example;

import org.example.gui.*;
import org.example.model.Hackathon;
import org.example.model.ruoli.Team;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {






       new Home();
        //new giudiceVediProgressiGUI();

        /*Hackathon hackathon=new Hackathon();
        creaListaTeam_votiRandom( hackathon,  19);
        stampaListaTeam(hackathon);

        Controller c=new Controller();
        c.ordinaTeamVoti(hackathon);
        stampaListaTeam(hackathon);*/



    }

    private static void creaListaTeam_votiRandom(Hackathon hackathon, int numeroTeam)
    {
        Random rand=new Random();
        for(int i=0;i<numeroTeam;i++)
        {
            Team team=new Team("Team_"+i,hackathon);
            team.setVoto(rand.nextFloat(100));
            hackathon.getListaTeam().add(team);
        }
    }

    private static void stampaListaTeam(Hackathon hackathon){
        System.out.println("\n");
        for(Team team: hackathon.getListaTeam())
            System.out.println(team.getNome()+"  voto:"+team.getVoto());
    }


}


/*DA RISOLVERE
* 1)  PARTECIPANTE,UTENTE: per il fatto che non si faccia a meno 2 giorni --> trovare modo per rappres. giorno attuale
* 2)  Rifare la creazione di una classifica
* 3) Migliorare alla fine le interfacce*/
