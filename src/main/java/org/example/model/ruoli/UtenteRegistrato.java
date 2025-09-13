package org.example.model.ruoli;

import org.example.model.Hackathon;
import org.example.model.Piattaforma;

public class UtenteRegistrato extends Utente {

    private static int nU =0;
    protected String email;
    protected String password;
    protected String id;

    private String richiestaGiudice="";
    private String hackIDRichiesta;


    /**
     * Costruttore della classe {@code UtenteRegistrato}.
     * Inizializza un nuovo utente registrato con nome, cognome, email e password.
     * Genera automaticamente un identificativo univoco tramite il metodo {@code codiceID()}.
     *
     * @param nome     nome dell'utente registrato
     * @param cognome  cognome dell'utente registrato
     * @param email    indirizzo email dell'utente registrato
     * @param password password associata all'account dell'utente registrato
     */
    public UtenteRegistrato(String nome, String cognome, String email, String password) {
        super(nome, cognome);
        this.email = email;
        this.password = password;
        this.id = codiceID();
    }


    /**
     * Costruttore della classe {@code UtenteRegistrato}.
     * Inizializza un nuovo utente registrato con un identificativo già esistente
     * e con i dati personali forniti.
     *
     * @param id       identificativo univoco dell'utente registrato
     * @param nome     nome dell'utente registrato
     * @param cognome  cognome dell'utente registrato
     * @param email    indirizzo email dell'utente registrato
     * @param password password associata all'account dell'utente registrato
     */
    public UtenteRegistrato(String id, String nome, String cognome, String email, String password){

        this.id = id;   this.nome=nome;   this.cognome=cognome;   this.email=email;   this.password=password;
    }

    /**
     * Costruttore vuoto della classe {@code UtenteRegistrato}.
     * Crea un oggetto {@code UtenteRegistrato} senza inizializzare i campi.
     */
    public UtenteRegistrato() {}

    /**
     * Restituisce l'indirizzo email dell'utente registrato.
     *
     * @return una {@link String} contenente l'email dell'utente
     */
    public String getEmail() {return email;}

    /**
     * Restituisce la password dell'utente registrato.
     *
     * @return una {@link String} contenente la password dell'utente
     */
    public String getPassword(){return password;}

    /**
     * Restituisce l'identificativo univoco dell'utente registrato.
     *
     * @return una {@link String} rappresentante l'ID dell'utente
     */
    public String getId() {return id;}

    /**
     * Genera un identificativo univoco per un nuovo utente registrato.
     * L'ID è costruito concatenando il prefisso {@code "U"} con il valore
     * corrente del contatore {@code nU}, formattato con zeri iniziali
     * per ottenere sempre una stringa di almeno tre cifre. Dopo la generazione,
     * il contatore {@code nU} viene incrementato di 1.
     *
     * @return una {@link String} rappresentante l'identificativo univoco dell'utente
     */
    private String codiceID(){
        String idCodice ="-1";
        if(nU >=0 && nU <10)  idCodice ="U00"+nU;

        else if (nU <100)   idCodice ="U0"+nU;

        else if (nU <1000)   idCodice ="U"+nU;

        nU++;

        return idCodice;
    }

    /**
     * Restituisce il valore corrente del contatore {@code nU}.
     * Questa variabile statica rappresenta il numero progressivo
     * utilizzato per la generazione degli identificativi degli utenti registrati.
     *
     * @return il valore corrente di {@code nU}
     */
    public static int getnU() {return nU;}


    /**
     * Imposta un nuovo valore per il contatore {@code nU}.
     * Questa variabile statica viene utilizzata per la generazione
     * degli identificativi progressivi degli utenti registrati.
     *
     * @param nU nuovo valore da assegnare a {@code nU}
     */
    public static void setnU(int nU) {
        UtenteRegistrato.nU = nU;}

    //IMPORTANTISSIMO L'ORDINE DELLE FUNZIONI CON QUELLA CHE LANCIA UN'ECCEZIONE


    /**
     * Registra l'utente in un team specifico di un hackathon.
     * Crea un nuovo oggetto {@link Partecipante} associato al team e all'hackathon,
     * lo aggiunge alla lista dei partecipanti del team e aggiorna la lista degli utenti
     * registrati sulla piattaforma rimuovendo l'utente originale.
     *
     * @param piattaforma la piattaforma in cui avviene la registrazione
     * @param hackathon   l'hackathon a cui il team partecipa
     * @param team        il team in cui registrare l'utente
     * @return il nuovo oggetto {@link Partecipante} creato e registrato
     * @throws IllegalArgumentException se il team ha già raggiunto il numero massimo di membri
     */
    public Partecipante registratiInTeam(Piattaforma piattaforma,Hackathon hackathon, Team team) throws IllegalArgumentException
    {
        Partecipante nuovoPartec=new Partecipante(this.getNome(), this.getCognome(), this.getEmail(), this.getPassword(), team.getId(), hackathon.getId());
        team.addPartecipante(nuovoPartec);//throws
        piattaforma.getListaUtenReg().add(nuovoPartec);
        piattaforma.getListaUtenReg().remove(this);
        return nuovoPartec;
    }

    /**
     * Crea un nuovo team e registra l'utente come partecipante all'interno di esso.
     * Il team viene associato all'hackathon specificato e aggiunto alla lista dei team
     * dell'hackathon. L'utente viene automaticamente registrato nel nuovo team.
     *
     * @param piattaforma la piattaforma in cui viene creato il team
     * @param hackathon   l'hackathon a cui il team partecipa
     * @param nomeTeam    il nome del nuovo team da creare
     * @return il nuovo oggetto {@link Team} creato
     * @throws IllegalArgumentException se il team ha già raggiunto il numero massimo di membri
     */
    public Team creaTeam(Piattaforma piattaforma, Hackathon hackathon, String nomeTeam) throws IllegalArgumentException{

        Team nuovoTeam=new Team(nomeTeam,hackathon);
        registratiInTeam(piattaforma,hackathon,nuovoTeam);//throws
        hackathon.addTeam(nuovoTeam);
        return nuovoTeam;

    }

    /**
     * Imposta il testo della richiesta dell'utente per diventare giudice.
     *
     * @param richiestaGiudice la richiesta da assegnare all'utente
     */
    public void setRichiestaGiudice(String richiestaGiudice) {this.richiestaGiudice = richiestaGiudice;}

    /**
     * Restituisce il testo della richiesta dell'utente per diventare giudice.
     *
     * @return una {@link String} contenente la richiesta dell'utente
     */
    public String getRichiestaGiudice() {return richiestaGiudice;}

    /**
     * Imposta l'identificativo dell'hackathon associato alla richiesta dell'utente.
     *
     * @param hackIDRichiesta l'ID dell'hackathon relativo alla richiesta
     */
    public void setHackIDRichiesta(String hackIDRichiesta) {this.hackIDRichiesta = hackIDRichiesta;}

    /**
     * Restituisce l'identificativo dell'hackathon associato alla richiesta dell'utente.
     *
     * @return una {@link String} rappresentante l'ID dell'hackathon della richiesta
     */
    public String getHackIDRichiesta() {return hackIDRichiesta;}
}