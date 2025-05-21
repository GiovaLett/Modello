package org.example.Model.ruoli;
import org.example.Model.Progresso;

import java.util.ArrayList;



public class Team {

    public float Voto=-1;
    public String Nome;
    String ID;
    String codice_accesso;
    int numero_membri=0;
    ArrayList<Progresso> ArrayProgresso = new ArrayList<>();
    ArrayList<Partecipante> ArrayPartecipante = new ArrayList<>();


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

    /**
     * MOSTRA CODICE ASSCESSO
     */
    public String getCodiceAccesso(){
        return this.codice_accesso;
    }




}
