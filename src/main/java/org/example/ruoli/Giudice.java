package org.example.ruoli;
import org.example.*;
import java.util.Scanner;


public class Giudice extends Utente_registrato
{
    public void CommentaProgressi(Progresso progresso)
    {
        String commento;
        Scanner scanner=new Scanner(System.in);
        System.out.println(progresso.lavoro);
        commento=scanner.next();
        progresso.commento=commento;
    }

    public void MostraProblema(Piattaforma piattaforma){
        piattaforma.vedi_problema=true;
    }

    public void ValutaTeam(Team team)
    {
        float voto;
        String str_float=scanner.nextLine();

        try {
            voto = Float.parseFloat(str_float);
            team.Voto=voto;
        }
        catch (Exception e)
        {
            System.out.println("Errore");
        }

    }




}
