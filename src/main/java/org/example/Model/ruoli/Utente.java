package org.example.Model.ruoli;
import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;

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
        String ID_codice="-1";
        if(n>=0 && n<10)  ID_codice="U00"+String.valueOf(n);

        else if (n<100)   ID_codice="U0"+String.valueOf(n);

        else if (n<1000)   ID_codice="U"+String.valueOf(n);

        n++;

        return ID_codice;
    }

    public void registrazione(Piattaforma piattaforma, Hackathon hackathon)
    {
        if (piattaforma.open_iscr && hackathon.getNumeroPartec() < hackathon.n_max_partec && hackathon.data.giorno-2>2)
        {


            String email;
            System.out.println("Inserisci email: ");
            email=scanner.next();
            String password;
            System.out.println("Inserisci password: ");
            password=scanner.next();


            Utente_registrato UteReg = new Utente_registrato(this.nome,this.cognome,email,password,this.Codice_ID());

            piattaforma.getListaUtenReg().add(UteReg);
        }

    }


}
