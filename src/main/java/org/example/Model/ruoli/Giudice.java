package org.example.Model.ruoli;
import org.example.Model.Hackathon;
import org.example.Model.Progresso;


public class Giudice extends Utente_registrato
{
    private static int nG =0;
    private String IDHackatlon;

    public Giudice(String nome,String cognome,String email,String password,String IDHackatlon){ //Non uso super perche poi potrebbe avanzare con la creazione di indirizzi ID di Utenete registrato
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.IDHackatlon=IDHackatlon;
        this.ID=Codice_ID();}

public Giudice(String ID,String nome,String cognome,String email,String password,String IDHackatlon){

        this.ID=ID;   this.nome=nome;   this.cognome=cognome;   this.email=email;   this.password=password;
        this.IDHackatlon=IDHackatlon;
}
    public String getIDHackatlon() {return IDHackatlon;}

    public void CommentaProgressi(Progresso progresso)
    {
        String commento="";

        progresso.setCommento(commento);//Carica il commento
    }

    public void MostraProblema(Hackathon hackathon){
        hackathon.setView_problema(true);
    }

    public void ValutaTeam(Team team,String votoStr) throws IllegalArgumentException
    {

       try {
           team.setVoto(Float.parseFloat(votoStr));//converto la stringa in float
           if (team.getVoto() < 0 || team.getVoto()>10)
               throw new Exception();
       } catch (Exception e) {
           throw new IllegalArgumentException("Valore inserito non valido\n(Deve essere compreso tra 0 e 10)");
       }

    }


    private String Codice_ID(){
        String ID_codice="-1";
        if(nG >=0 && nG <10)  ID_codice="G00"+String.valueOf(nG);

        else if (nG <100)   ID_codice="G0"+String.valueOf(nG);

        else if (nG <1000)   ID_codice="G"+String.valueOf(nG);

        nG++;

        return ID_codice;
    }

    public static int getnG() {
        return nG;
    }

    public static void setnG(int nG) {
        Giudice.nG = nG;
    }
}
