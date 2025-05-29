package org.example.Model.ruoli;
import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;

import java.util.Scanner;

public class Utente
{
    String nome;
    String cognome;


    public Utente(){}

    public String getNome() {return nome;}

    public String getCognome() {return cognome;}

    public Utente(String nome, String cognome)
    {
        this.nome=nome;
        this.cognome=cognome;
    }

}
