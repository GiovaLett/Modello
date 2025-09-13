package org.example.model.ruoli;

public class Partecipante extends UtenteRegistrato {

    private static int nP =0;  //Per la creazione di un codice ID del team nel caso venga creato

    private final String idTeam;
    private final String idHackathon;


    /**
     * Costruttore della classe {@code Partecipante}.
     * Inizializza un nuovo partecipante con i dati personali e le informazioni
     * relative al team e all'hackathon di appartenenza. L'identificativo univoco
     * del partecipante viene generato automaticamente tramite il metodo
     * {@code creaIDPartecip()}.
     *
     * @param nome        nome del partecipante
     * @param cognome     cognome del partecipante
     * @param email       indirizzo email del partecipante
     * @param password    password associata all'account del partecipante
     * @param idTeam      identificativo del team a cui il partecipante appartiene
     * @param idHackathon identificativo dell'hackathon a cui il partecipante partecipa
     */
    public Partecipante(String nome,String cognome,String email,String password,String idTeam,String idHackathon){
        super(nome,cognome,email,password);
        this.id = creaIDPartecip();
        this.idTeam = idTeam;
        this.idHackathon = idHackathon;

    }


    /**
     * Costruttore della classe {@code Partecipante}.
     * Inizializza un nuovo partecipante con un identificativo già esistente,
     * i dati personali e le informazioni relative al team e all'hackathon di appartenenza.
     * Questo costruttore è utile quando si ricrea un oggetto {@code Partecipante}
     * a partire da dati già presenti in un database.
     *
     * @param id          identificativo univoco del partecipante
     * @param nome        nome del partecipante
     * @param cognome     cognome del partecipante
     * @param email       indirizzo email del partecipante
     * @param password    password associata all'account del partecipante
     * @param idTeam      identificativo del team a cui il partecipante appartiene
     * @param idHackathon identificativo dell'hackathon a cui il partecipante partecipa
     */
    public Partecipante(String id, String nome, String cognome, String email, String password, String idTeam, String idHackathon){
        super(nome,cognome,email,password);
        this.id = id;   this.idTeam = idTeam;   this.idHackathon = idHackathon;

    }


    /**
     * Restituisce il valore corrente del contatore {@code nP}.
     * Questa variabile statica rappresenta il numero progressivo
     * utilizzato per la generazione degli identificativi dei partecipanti.
     *
     * @return il valore corrente di {@code nP}
     */
    public static int getnP() { return nP;}

    /**
     * Imposta un nuovo valore per il contatore {@code nP}.
     * Questa variabile statica viene utilizzata per la generazione
     * degli identificativi progressivi dei partecipanti.
     *
     * @param nP nuovo valore da assegnare a {@code nP}
     */
    public static void setnP(int nP) {Partecipante.nP = nP;}


    /**
     * Genera un identificativo univoco per un nuovo partecipante.
     * L'ID è costruito concatenando il prefisso {@code "P"} con il valore
     * corrente del contatore {@code nP}, formattato con zeri iniziali
     * per ottenere sempre una stringa di almeno tre cifre. Dopo la generazione,
     * il contatore {@code nP} viene incrementato di 1.
     *
     * @return una {@link String} rappresentante l'identificativo univoco del partecipante
     */
    private String creaIDPartecip(){

        String idCodice ="-1";
        if(nP >=0 && nP <10)  idCodice ="P00"+nP;

        else if (nP <100)   idCodice ="P0"+nP;

        else if (nP <1000)   idCodice ="P"+nP;

        nP++;

        return idCodice;

    }



    public String getIdTeam() {return idTeam;}

    public String getIdHackathon() {return idHackathon;}
}