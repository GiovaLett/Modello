package org.example.Model.ruoli;
import org.example.Model.Piattaforma;
import org.example.Model.Progresso;

import java.util.Scanner;


public class Giudice extends Utente_registrato
{
    private static int n=0;
    private String IDHackatlon;

    public Giudice(String nome,String cognome,String email,String password,String IDHackatlon){
        super(nome,cognome,email,password);
        this.IDHackatlon=IDHackatlon;
        this.ID=Codice_ID();}


    public String getIDHackatlon() {return IDHackatlon;}

    public void CommentaProgressi(Progresso progresso)
    {
        String commento="";

        progresso.commento=commento;//Carica il commento
    }

    public void MostraProblema(Piattaforma piattaforma){
        piattaforma.setView_problema(true);
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


    private String Codice_ID(){
        String ID_codice="-1";
        if(n>=0 && n<10)  ID_codice="G00"+String.valueOf(n);

        else if (n<100)   ID_codice="G0"+String.valueOf(n);

        else if (n<1000)   ID_codice="G"+String.valueOf(n);

        n++;

        return ID_codice;
    }




}
