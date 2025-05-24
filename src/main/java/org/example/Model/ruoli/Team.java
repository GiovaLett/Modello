package org.example.Model.ruoli;
import org.example.Model.Hackathon;
import org.example.Model.Progresso;

import java.util.ArrayList;



public class Team {

    private static int nt=0;
    private final int maxMembri=5;
    float Voto=-1;
    String Nome;
    String ID;
    String codice_accesso;
    int numero_membri=0;
    ArrayList<Progresso> ArrayProgresso = new ArrayList<>();
    ArrayList<Partecipante> ArrayPartecipante = new ArrayList<>();
    String IDHackathon;

    public String getID() {
        return ID;
    }

    public String getNome() {
        return Nome;
    }

    public int getNumero_membri() {
        return numero_membri;
    }

    public String getCodiceAccesso(){
        return this.codice_accesso;
    }

    public String getIDHackathon() {return IDHackathon;}

    public Team(String nome, Hackathon hackathon){
        this.ID=CreaIDTeam();  this.IDHackathon=hackathon.getID();  this.Nome=nome;
    }

    public void addPartecipante(Partecipante partec) throws IllegalArgumentException{

        if(numero_membri<maxMembri)
        {
            this.ArrayPartecipante.add(partec);
            numero_membri++;
        }

        else
            throw new IllegalArgumentException("Il team scelto ha raggiunto il numero massimo di membri");
    }

    /**
     * CARICAMENTO PROGRESSI
     */
    public void caricamento_progressi(String cod_programma)
    {

        Progresso progress =new Progresso();
        progress.lavoro= cod_programma;
        this.ArrayProgresso.add(progress);
    }


    private String CreaIDTeam(){

        String ID_codice="-1";
        if(nt >=0 && nt <10)  ID_codice="T00"+String.valueOf(nt);

        else if (nt <100)   ID_codice="T0"+String.valueOf(nt);

        else if (nt <1000)   ID_codice="T"+String.valueOf(nt);

        nt++;

        return ID_codice;

    }







}
