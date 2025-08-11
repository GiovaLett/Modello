package org.example.model.ruoli;

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
