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

    /**
     * Restituisce lo stato delle iscrizioni all'hackathon.
     *
     * @return {@code true} se le iscrizioni sono aperte, {@code false} altrimenti
     */
    public boolean isOpenIscr() {return openIscr;}

    /**
     * Imposta lo stato delle iscrizioni all'hackathon.
     *
     * @param openIscr {@code true} se le iscrizioni devono essere aperte, {@code false} altrimenti
     */
    public void setOpenIscr(boolean openIscr) {this.openIscr = openIscr;}

    /**
     * Restituisce lo stato di preparazione dell'evento hackathon.
     *
     * @return {@code true} se l'evento è pronto per iniziare, {@code false} altrimenti
     */
    public boolean isEventoPronto() {return eventoPronto;}

    /**
     * Imposta lo stato di preparazione dell'evento hackathon.
     *
     * @param eventoPronto {@code true} se l'evento è pronto per iniziare, {@code false} altrimenti
     */
    public void setEventoPronto(boolean eventoPronto) {this.eventoPronto = eventoPronto;}

    /**
     * Restituisce la lista degli hackathon presenti sulla piattaforma.
     *
     * @return un {@link ArrayList} di oggetti {@link Hackathon} rappresentanti tutti gli hackathon della piattaforma
     */
    public ArrayList<Hackathon> getListaHackathon() {return listaHackathon;}


    /**
     * Aggiunge un hackathon alla lista degli hackathon presenti sulla piattaforma.
     *
     * @param hackathon l'oggetto {@link Hackathon} da aggiungere alla piattaforma
     */
    public void addHackathon(Hackathon hackathon){
        listaHackathon.add(hackathon);}

    /**
     * Restituisce la lista degli utenti registrati sulla piattaforma.
     *
     * @return un {@link ArrayList} di oggetti {@link UtenteRegistrato} rappresentanti tutti gli utenti registrati
     */
    public ArrayList<UtenteRegistrato> getListaUtenReg() {return listaUtenReg;}

    /**
     * Aggiunge un nuovo utente registrato alla piattaforma.
     *
     * @param nuovoUtente l'oggetto {@link UtenteRegistrato} da aggiungere alla lista degli utenti registrati
     */
    public void addUtenteReg(UtenteRegistrato nuovoUtente){
        listaUtenReg.add(nuovoUtente);}


    public void setDataEvento(String giorno,String mese,String anno){
        String dataInput=anno+"-"+mese+"-"+giorno;
        dataEvento=LocalDate.parse(dataInput, DateTimeFormatter.ISO_LOCAL_DATE);
    }


    /**
     * Imposta la data dell'evento della piattaforma a partire da una stringa.
     * La stringa deve essere nel formato (yyyy-MM-dd).
     *
     * @param dataStr la data dell'evento come {@link String}
     */
    public void setDataEvento(String dataStr){

        dataEvento=LocalDate.parse(dataStr, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Imposta la data dell'evento della piattaforma.
     *
     * @param dataEvento la data dell'evento come oggetto {@link LocalDate}
     */
    public void setDataEvento(LocalDate dataEvento){    this.dataEvento=dataEvento;}


    /**
     * Restituisce la data dell'evento della piattaforma.
     *
     * @return un oggetto {@link LocalDate} rappresentante la data dell'evento
     */
    public LocalDate getDataEvento(){return dataEvento;}



}

