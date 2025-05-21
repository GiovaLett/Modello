package org.example.Model.ruoli;
import org.example.Model.Piattaforma;
import org.example.Model.Progresso;

import java.util.Scanner;


public class Giudice extends Utente_registrato
{
    public void CommentaProgressi(Progresso progresso)
    {
        String commento;
        Scanner scanner=new Scanner(System.in);
        System.out.println(progresso.lavoro); //Visualizza il lavoro

        System.out.println("\n\n Commento:");//Commenta il lavoro
        commento=scanner.next();
        progresso.commento=commento;//Carica il commento
    }

    public void MostraProblema(Piattaforma piattaforma){
        piattaforma.view_problema =true;
    }

    public void ValutaTeam(Team team)
    {
        float voto;
        Scanner scanner=new Scanner(System.in);
        String str_float=scanner.nextLine();

        try {
            voto = Float.parseFloat(str_float);//converto la stringa in float
            team.Voto=voto;
        }
        catch (Exception e)
        {
            System.out.println("Errore");//se non viene convertito correttamente stampa errore
        }

    }




}
