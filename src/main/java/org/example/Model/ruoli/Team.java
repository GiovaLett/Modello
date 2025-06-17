package org.example.Model.ruoli;
import org.example.Model.Hackathon;
import org.example.Model.Progresso;

import java.util.ArrayList;
import java.util.Random;


public class Team {



    private String Nome;

    private String codice_accesso;

    String IDHackathon;
    private String ID;
    private static int nT =0;

    private final int MAX_MEMBRI =1;
    int numeroMembri =0;

    float voto =-1;//Valore di default indica che ancora non ha un voto

    ArrayList<Progresso> ArrayProgresso = new ArrayList<>();
    ArrayList<Partecipante> ArrayPartecipante = new ArrayList<>();




    public Team(String nome, Hackathon hackathon){
        this.codice_accesso=creaCodiceAccesso();   this.ID=CreaIDTeam();  this.IDHackathon=hackathon.getID();  this.Nome=nome;
    }

    public String getID() {return ID;}



    public String getNome() {return Nome;}

    public int getNumeroMembri() {return numeroMembri;}

    public String getCodiceAccesso(){
        return this.codice_accesso;
    }

    public String getIDHackathon() {return IDHackathon;}

    public float getVoto() {return voto;}

    public void setVoto(float voto) {this.voto = voto;}

    public ArrayList<Partecipante> getArrayPartecipante() {return ArrayPartecipante;}
    public void addPartecipante(Partecipante partec) throws IllegalArgumentException{

        if(numeroMembri < MAX_MEMBRI)
        {
            this.ArrayPartecipante.add(partec);
            numeroMembri++;
        }

        else
            throw new IllegalArgumentException("Il team scelto ha raggiunto il numero massimo di membri");
    }

    /**
     * CARICAMENTO PROGRESSI
     */
    public void caricaProgresso(String nomeProgresso,String codiceProgresso)
    {

        Progresso progresso =new Progresso();
        progresso.setCodiceProgresso(codiceProgresso);
        progresso.setNome(nomeProgresso);
        this.ArrayProgresso.add(progresso);
    }

    public ArrayList<Progresso> getArrayProgresso() {return ArrayProgresso;}

    private String CreaIDTeam(){

        String ID_codice="-1";
        if(nT >=0 && nT <10)  ID_codice="T00"+String.valueOf(nT);

        else if (nT <100)   ID_codice="T0"+String.valueOf(nT);

        else if (nT <1000)   ID_codice="T"+String.valueOf(nT);

        nT++;

        return ID_codice;

    }



    private String creaCodiceAccesso(){
        char[] lettere={'A','B','C','D','E','F','G','H','I','L','J','K','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] numeri={'0','1','2','3','4','5','6','7','8','9'};


        StringBuilder codiceAccesso= new StringBuilder();
        Random random=new Random();

        for(int i=0;i<7;i++)
        {
            if(i<2 || i>4)
                codiceAccesso.append(lettere[random.nextInt(0, lettere.length)]);
            else
                codiceAccesso.append(numeri[random.nextInt(0, numeri.length)]);
        }

        return codiceAccesso.toString();
    }







}
