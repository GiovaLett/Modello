package org.example.Model;
import org.example.Model.ruoli.*;

import java.util.ArrayList;

public class Piattaforma
{
    private boolean open_iscr = false;// indica la possibilità di iscriversi
    private boolean eventoPronto=false;//quando si chiudono le iscrizioni, perchè dovra iniziare l'hackathon


    ArrayList<Utente_registrato> ListaUtenReg =new ArrayList<>();
    ArrayList<Hackathon> ListaHackathon=new ArrayList<>();


    public boolean isOpen_iscr() {return open_iscr;}
    public void setOpen_iscr(boolean open_iscr) {this.open_iscr = open_iscr;}

    public boolean isEventoPronto() {return eventoPronto;}
    public void setEventoPronto(boolean eventoPronto) {this.eventoPronto = eventoPronto;}

    public ArrayList<Hackathon> getListaHackathon() {return ListaHackathon;}
    public void addHackathon(Hackathon hackathon){ListaHackathon.add(hackathon);}

    public ArrayList<Utente_registrato> getListaUtenReg() {return ListaUtenReg;}
    public void addUtenteReg(Utente_registrato nuovoUtente){ListaUtenReg.add(nuovoUtente);}




    public void buildListaHackathon(int n)
    {
        for(int i=0; i<n ; i++)
        {
            Hackathon hackathon=new Hackathon();
            hackathon.setNome("Hackathon"+i);
            ListaHackathon.add(hackathon);
        }
    }

}

