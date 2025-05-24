package org.example.Model.ruoli;
import org.example.Model.Progresso;

import java.util.ArrayList;



public class Team {

    float Voto=-1;
    String Nome;
    String ID;
    String codice_accesso;
    int numero_membri=0;
    ArrayList<Progresso> ArrayProgresso = new ArrayList<>();
    ArrayList<Partecipante> ArrayPartecipante = new ArrayList<>();

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



    public Team(String nome){};

    /**
     * CARICAMENTO PROGRESSI
     */
    public void caricamento_progressi(String cod_programma)
    {

        Progresso progress =new Progresso();
        progress.lavoro= cod_programma;
        this.ArrayProgresso.add(progress);
    }







}
