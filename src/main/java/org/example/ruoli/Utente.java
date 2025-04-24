package org.example.ruoli;
import org.example.*;

import java.util.Scanner;

public class Utente
{
    private static int n=0;
    public String nome;
    public String cognome;

    Scanner scanner = new Scanner(System.in);

    public Utente(){}

    public Utente(String nome,String cognome)
    {
        this.nome=nome;
        this.cognome=cognome;
    }


    private String Codice_ID(){
    String ID_codice="ID_"+String.valueOf(n);
    n++;
    return ID_codice;
    }

    public void registrazione(Piattaforma piattaforma, Hackathon hackathon)
    {
        if (piattaforma.iscrizione && hackathon.n_partec < hackathon.n_max_partec && hackathon.data-2>2)
        {


            String email;
            System.out.println("Inserisci email: ");
            email=scanner.next();
            String password;
            System.out.println("Inserisci password: ");
            password=scanner.next();


            Utente_registrato UteReg = new Utente_registrato(this.nome,this.cognome,email,password,this.Codice_ID());

            piattaforma.ArrayUtenReg.add(UteReg);
        }

    }


}
