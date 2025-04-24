package org.example.ruoli;
import org.example.*;


import java.util.Scanner;

public class Partecipante extends Utente_registrato {




/**
 * ISCRIZIONE AL TEAM
 */
    public void entraInTeam(Piattaforma piattaforma, Hackathon hackathon)
    {
        boolean trovato=false;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il codice del team: ");
        String codice = scanner.nextLine();

        if(piattaforma.iscrizione && hackathon.data-2>2)
        {
            for(Team team:piattaforma.ArrayTeam)
            {
                if(team.codice_accesso.equals(codice))
                {
                    if(team.numero_membri < hackathon.dim_max_team)
                    {
                        trovato=true;
                        team.ArrayPartecipante.add(this);
                        team.numero_membri++;

                    }
                    else
                        System.out.println("ERRORE: Numero membri massimo");
                }
                if(!trovato)
                    System.out.println("Codice inesistente");
            }
        }
        else System.out.println("Iscrizioni chiuse");
    }

    /**
     * CREAZIONE DEL TEAM
     */
    public void CreaTeam(Piattaforma piattaforma, Hackathon hackathon)
    {
        if(piattaforma.iscrizione && hackathon.data-2>2)
        {
            Scanner scanner = new Scanner(System.in);
            String nome_team=scanner.nextLine();
            Team team=new Team(nome_team);
            team.numero_membri++;
            team.ArrayPartecipante.add(this);
            team.ID="0003";

            piattaforma.ArrayTeam.add(team);
            piattaforma.n_team++;
        }
        else System.out.println("Iscrizioni chiuse");
    }


}