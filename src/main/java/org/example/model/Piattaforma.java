package org.example.model;
import org.example.model.ruoli.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Piattaforma
{
    private boolean openIscr = false;// indica la possibilità di iscriversi
    private boolean eventoPronto=false;//quando si chiudono le iscrizioni, perchè dovra iniziare l'hackathon


    ArrayList<UtenteRegistrato> listaUtenReg =new ArrayList<>();
    ArrayList<Hackathon> listaHackathon =new ArrayList<>();
    LocalDate dataEvento=null;

    public boolean isOpenIscr() {return openIscr;}
    public void setOpenIscr(boolean openIscr) {this.openIscr = openIscr;}

    public boolean isEventoPronto() {return eventoPronto;}
    public void setEventoPronto(boolean eventoPronto) {this.eventoPronto = eventoPronto;}

    public ArrayList<Hackathon> getListaHackathon() {return listaHackathon;}
    public void addHackathon(Hackathon hackathon){
        listaHackathon.add(hackathon);}

    public ArrayList<UtenteRegistrato> getListaUtenReg() {return listaUtenReg;}
    public void addUtenteReg(UtenteRegistrato nuovoUtente){
        listaUtenReg.add(nuovoUtente);}

    public void setDataEvento(String giorno,String mese,String anno){
        String dataInput=anno+"-"+mese+"-"+giorno;
        dataEvento=LocalDate.parse(dataInput, DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public void setDataEvento(String dataStr){

        dataEvento=LocalDate.parse(dataStr, DateTimeFormatter.ISO_LOCAL_DATE);
    }


    public void setDataEvento(LocalDate dataEvento){    this.dataEvento=dataEvento;}
    public LocalDate getDataEvento(){return dataEvento;}



}

