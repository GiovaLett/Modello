package org.example.model;

import org.example.model.ruoli.*;


import java.util.ArrayList;

public class Hackathon
{


    private String nome;
    private String id;
    private static int nH =0;//Per la creazione dei codiciID

    public static final int N_MAX_PARTEC =50;
    private int nPartec =0;

    private int nTeam;

    private int durata;
    private String sede;

    private String problema="";
    ArrayList<Team> listaTeam =new ArrayList<>();
    ArrayList<Giudice> listaGiudici =new ArrayList<>();


    //Ordine temporale di attivazione
    private boolean teamSuffic =false;
    private boolean viewProblema =false;
    private boolean eventoFinito=false;
    private boolean votazioneConclusa =false;
    private boolean classificaPubblicata=false;


    /**
     * Costruttore vuoto della classe {@code Hackathon}.
     * Inizializza un nuovo hackathon generando automaticamente un identificativo univoco
     * tramite il metodo {@code codiceID()}.
     */
    public Hackathon(){ id = codiceID();}

    /**
     * Costruttore della classe {@code Hackathon}.
     * Inizializza un nuovo hackathon assegnando il nome fornito
     * e generando automaticamente un identificativo univoco tramite {@code codiceID()}.
     *
     * @param nome il nome dell'hackathon
     */
    public Hackathon(String nome){this.nome=nome;  id = codiceID();}


    /**
     * Costruttore completo della classe {@code Hackathon}.
     * Permette di creare un oggetto {@code Hackathon} con tutti i campi inizializzati.
     * Utile per ricreare hackathon a partire da dati esistenti, ad esempio dal database.
     *
     * @param id                  identificativo univoco dell'hackathon
     * @param nome                nome dell'hackathon
     * @param nTeam               numero di team partecipanti
     * @param problema            descrizione del problema dell'hackathon
     * @param teamSuff             flag che indica se il numero minimo di team è sufficiente
     * @param viewProblema         flag che indica se il problema è visibile ai partecipanti
     * @param eventoFinito         flag che indica se l'evento è terminato
     * @param votazConclusa        flag che indica se la votazione è conclusa
     * @param classificaPubblicata flag che indica se la classifica è stata pubblicata
     * @param sede                 sede dell'hackathon
     * @param durata               durata dell'hackathon in giorni
     */
   public  Hackathon(String id, String nome, int nTeam, String problema,
                     boolean teamSuff, boolean viewProblema, boolean eventoFinito, boolean votazConclusa, boolean classificaPubblicata, String sede, int durata){

        this.id =id;  this.nome=nome;   this.nTeam = nTeam;   this.problema=problema;

        this.teamSuffic = teamSuff;  this.viewProblema = viewProblema;  this.eventoFinito= eventoFinito;

        this.votazioneConclusa= votazConclusa;  this.classificaPubblicata=classificaPubblicata;
        this.sede=sede;     this.durata=durata;

    }

    /**
     * Imposta l'identificativo univoco dell'hackathon.
     *
     * @param id il nuovo identificativo da assegnare all'hackathon
     */
    public void setId(String id) {this.id = id;}

    /**
     * Restituisce l'identificativo univoco dell'hackathon.
     *
     * @return una {@link String} rappresentante l'ID dell'hackathon
     */
    public String getId() {return id;}

    /**
     * Restituisce il nome dell'hackathon.
     *
     * @return una {@link String} contenente il nome dell'hackathon
     */
    public String getNome(){return nome;}

    /**
     * Imposta il nome dell'hackathon.
     *
     * @param nome il nuovo nome da assegnare all'hackathon
     */
    public void setNome(String nome){this.nome=nome;}

    /**
     * Imposta la durata dell'hackathon.
     *
     * @param durata la durata dell'hackathon in giorni
     */
    public void setDurata(int durata) {this.durata = durata;}

    /**
     * Restituisce la durata dell'hackathon.
     *
     * @return un intero rappresentante la durata dell'hackathon in giorni
     */
    public int getDurata() {return durata;}

    /**
     * Imposta la sede dell'hackathon.
     *
     * @param sede la nuova sede da assegnare all'hackathon
     */
    public void setSede(String sede) {this.sede = sede;}

    /**
     * Restituisce la sede dell'hackathon.
     *
     * @return una {@link String} contenente la sede dell'hackathon
     */
    public String getSede() {return sede;}


    /**
     * Restituisce la lista dei team partecipanti all'hackathon.
     *
     * @return un {@link ArrayList} di oggetti {@link Team} rappresentanti i team dell'hackathon
     */
    public ArrayList<Team> getListaTeam() {return listaTeam;}

    /**
     * Restituisce la lista dei giudici assegnati all'hackathon.
     *
     * @return un {@link ArrayList} di oggetti {@link Giudice} rappresentanti i giudici dell'hackathon
     */
    public ArrayList<Giudice> getListaGiudici(){return listaGiudici;}

    /**
     * Restituisce il numero di partecipanti iscritti all'hackathon.
     *
     * @return un intero rappresentante il numero di partecipanti
     */
    public int getNumeroPartec() {return nPartec;}


    /**
     * Incrementa il numero di partecipanti iscritti all'hackathon di 1.
     * Lancia un'eccezione se il numero massimo di partecipanti è già stato raggiunto.
     *
     * @throws IllegalArgumentException se il numero di partecipanti ha già raggiunto {@code N_MAX_PARTEC}
     */
    public void incrementaNpartec() throws IllegalArgumentException //Se n_partec==n_max
    {
        if(nPartec < N_MAX_PARTEC)
            nPartec++;
        else
            throw new IllegalArgumentException("Numero di partecipanti massimo raggiunto per questo hackathon\n" +
                    "Impossibile iscriversi");

    }



    //Se è presente un solo team l'hackaton non si fa

    /**
     * Aggiorna il flag {@code teamSuffic} in base al numero di team partecipanti.
     * Se il numero di team è minore di 2, l'hackathon non è considerato valido
     * per iniziare e {@code teamSuffic} viene impostato a {@code false};
     * altrimenti viene impostato a {@code true}.
     */
    public void fareHackathonFromNTeams(){
        if(nTeam <2)
            teamSuffic =false;
        else teamSuffic =true;
    }

    /**
     * Imposta la descrizione del problema dell'hackathon.
     *
     * @param problema la descrizione del problema da assegnare all'hackathon
     */
    public void setProblema(String problema) {this.problema = problema;}

    /**
     * Restituisce la descrizione del problema dell'hackathon.
     *
     * @return una {@link String} contenente il problema dell'hackathon
     */
    public String getProblema() {return problema;}


    /**
     * Aggiunge un giudice alla lista dei giudici dell'hackathon.
     *
     * @param giudice l'oggetto {@link Giudice} da aggiungere all'hackathon
     */
    public void addGiudice(Giudice giudice){
        listaGiudici.add(giudice);}


    /**
     * Aggiunge un team alla lista dei team partecipanti all'hackathon
     * e incrementa il contatore dei team.
     *
     * @param team l'oggetto {@link Team} da aggiungere all'hackathon
     */
    public void addTeam(Team team){
        listaTeam.add(team);
        nTeam++;}

    /**
     * Restituisce lo stato di visibilità del problema dell'hackathon.
     *
     * @return {@code true} se il problema è visibile ai partecipanti, {@code false} altrimenti
     */
    public boolean isViewProblema() {return viewProblema;}

    /**
     * Imposta la visibilità del problema dell'hackathon ai partecipanti.
     *
     * @param viewProblema {@code true} se il problema deve essere visibile, {@code false} altrimenti
     */
    public void setViewProblema(boolean viewProblema) {this.viewProblema = viewProblema;}

    /**
     * Restituisce lo stato della votazione dell'hackathon.
     *
     * @return {@code true} se la votazione è stata conclusa, {@code false} altrimenti
     */
    public boolean isVotazioneConclusa() {return votazioneConclusa;}

    /**
     * Imposta lo stato della votazione dell'hackathon.
     *
     * @param votazioneConclusa {@code true} se la votazione è conclusa, {@code false} altrimenti
     */
    public void setVotazioneConclusa(boolean votazioneConclusa) {
        this.votazioneConclusa = votazioneConclusa;}

    /**
     * Restituisce lo stato dell'evento dell'hackathon.
     *
     * @return {@code true} se l'evento è terminato, {@code false} altrimenti
     */
    public boolean isEventoFinito(){return eventoFinito;}

    /**
     * Imposta lo stato dell'evento dell'hackathon.
     *
     * @param isEventoFinito {@code true} se l'evento è terminato, {@code false} altrimenti
     */
    public void setEventoFinito(boolean isEventoFinito){this.eventoFinito=isEventoFinito;}

    /**
     * Imposta lo stato della pubblicazione della classifica dell'hackathon.
     *
     * @param classificaPubblicata {@code true} se la classifica è stata pubblicata, {@code false} altrimenti
     */
    public void setClassificaPubblicata(boolean classificaPubblicata) {this.classificaPubblicata = classificaPubblicata;}

    /**
     * Restituisce lo stato della pubblicazione della classifica dell'hackathon.
     *
     * @return {@code true} se la classifica è stata pubblicata, {@code false} altrimenti
     */
    public boolean isClassificaPubblicata() {return classificaPubblicata;}

    /**
     * Restituisce lo stato della sufficienza dei team per l'hackathon.
     *
     * @return {@code true} se ci sono abbastanza team per procedere con l'hackathon, {@code false} altrimenti
     */
    public boolean isTeamSuffic() {return teamSuffic;}

    /**
     * Genera un ID univoco per l'hackathon basato sul contatore statico {@code nH}.
     * L'ID viene formattato con prefissi "H" e zeri iniziali a seconda del valore di {@code nH}.
     *
     * @return una {@link String} rappresentante l'ID univoco dell'hackathon
     */
    private String codiceID(){
        String idCodice ="-1";
        if(nH >=0 && nH <10)  idCodice ="H00"+nH;

        else if (nH <100)   idCodice ="H0"+nH;

        else if (nH <1000)   idCodice ="H"+nH;

        nH++;

        return idCodice;
    }

    /**
     * Restituisce il contatore statico degli hackathon creati.
     *
     * @return il valore corrente di {@code nH}, che rappresenta il numero di hackathon generati
     */
    public static int getnH() {return nH;}


    /**
     * Imposta il contatore statico degli hackathon creati.
     *
     * @param nH il nuovo valore del contatore {@code nH} da assegnare
     */
    public static void setnH(int nH) {
        Hackathon.nH = nH;
    }
}
