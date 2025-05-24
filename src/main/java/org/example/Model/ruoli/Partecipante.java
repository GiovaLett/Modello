package org.example.Model.ruoli;
import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;


import java.util.Scanner;

public class Partecipante extends Utente_registrato {

    private static int n=0;  //Per la creazione di un codice ID del team nel caso venga creato
    private boolean inAteam=false;


/**
 * ISCRIZIONE AL TEAM
 */
    public void entraInTeam(Piattaforma piattaforma, Hackathon hackathon)
    {
        boolean trovato=false;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il codice del team: ");
        String codice = scanner.nextLine();

        if(piattaforma.isOpen_iscr() && hackathon.data.giorno-2>2)//per il fatto che non si faccia a meno 2 giorni --> trovare modo per rappres. giorno attuale
        {
            for(Team team: hackathon.getListaTeam())
            {
                if(team.codice_accesso.equals(codice))
                {
                    if(team.numero_membri < hackathon.dim_max_team)
                    {
                        trovato=true;
                        team.ArrayPartecipante.add(this);
                        team.numero_membri++;
                        break;

                    }
                    else
                        System.out.println("ERRORE: Numero membri massimo");
                }
            }
            if(!trovato)
                System.out.println("Codice inesistente");
        }
        else System.out.println("Iscrizioni chiuse");
    }



    /**
     * CREAZIONE DEL TEAM
     */
    public void CreaTeam(Piattaforma piattaforma, Hackathon hackathon)
    {
        if(piattaforma.isOpen_iscr() && hackathon.data.giorno-2>2)
        {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Inserisci nome del Team:");
            String nome_team=scanner.nextLine();
            Team team=new Team(nome_team);

            team.numero_membri++;
            team.ArrayPartecipante.add(this);
            team.ID=CreaIDTeam();

            hackathon.getListaTeam().add(team);// anche se termina la funzione e team(variabile locale ) viene eliminata, l'istanza creata di Team rimane per ArrayTeam che contiene ora un riferimento ad essa
            hackathon.incrementaNpartec();
        }
        else System.out.println("Iscrizioni chiuse");
    }


    private String CreaIDTeam(){

            String ID_codice="-1";
            if(n>=0 && n<10)  ID_codice="T00"+String.valueOf(n);

            else if (n<100)   ID_codice="T0"+String.valueOf(n);

            else if (n<1000)   ID_codice="T"+String.valueOf(n);

            n++;

            return ID_codice;

    }

    public void setInAteam(boolean inAteam) {
        this.inAteam = inAteam;
    }

    public boolean getInAteam(){return this.inAteam;}
}