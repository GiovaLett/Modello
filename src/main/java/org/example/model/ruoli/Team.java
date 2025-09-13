package org.example.model.ruoli;
import org.example.model.Hackathon;
import org.example.model.Progresso;

import java.util.ArrayList;
import java.util.Random;


public class Team {



    private String nome;

    private String codiceAccesso;

    String idHackathon;
    private String id;
    private static int nT =0;

    private static final int MAX_MEMBRI =5;
    int numeroMembri =0;

    float voto =-1;//Valore di default indica che ancora non ha un voto

    ArrayList<Progresso> arrayProgresso = new ArrayList<>();
    ArrayList<Partecipante> arrayPartecipante = new ArrayList<>();


    /**
     * Costruttore della classe {@code Team}.
     * Inizializza un nuovo team con un identificativo, un nome, un codice di accesso,
     * un voto e l'associazione a un hackathon specifico.
     *
     * @param id            identificativo univoco del team
     * @param nome          nome del team
     * @param codiceAccesso codice di accesso del team
     * @param voto          voto assegnato al team (può essere inizialmente 0)
     * @param idHackathon   identificativo dell'hackathon a cui il team partecipa
     */
    public Team(String id, String nome, String codiceAccesso, float voto, String idHackathon){

        this.id =id;   this.nome=nome;   this.codiceAccesso = codiceAccesso;
        this.voto=voto;   this.idHackathon =idHackathon;
    }


    /**
     * Costruttore della classe {@code Team}.
     * Crea un nuovo team associato a un hackathon specifico generando automaticamente
     * un identificativo unico e un codice di accesso. Il nome del team viene assegnato
     * come fornito dal parametro.
     *
     * @param nome      nome del team
     * @param hackathon hackathon a cui il team partecipa; il team eredita l'identificativo dell'hackathon
     */
    public Team(String nome, Hackathon hackathon){
        this.codiceAccesso =creaCodiceAccesso();   this.id = creaIDTeam();  this.idHackathon =hackathon.getId();  this.nome =nome;
    }

    /**
     * Restituisce l'identificativo univoco del team.
     *
     * @return una {@link String} rappresentante l'ID del team
     */
    public String getId() {return id;}


    /**
     * Restituisce il nome del team.
     *
     * @return una {@link String} contenente il nome del team
     */
    public String getNome() {return nome;}

    /**
     * Restituisce il numero dei membri del team.
     *
     *  @return un {@code int} rappresentante il numero di membri del team
     */
    public int getNumeroMembri() {return numeroMembri;}


    /**
     * Restituisce il codice di accesso del team.
     * Questo codice può essere utilizzato per identificare o autenticare i membri del team.
     *
     * @return una {@link String} contenente il codice di accesso del team
     */
    public String getCodiceAccesso(){
        return this.codiceAccesso;
    }

    /**
     * Restituisce l'identificativo dell'hackathon a cui il team partecipa.
     *
     * @return una {@link String} rappresentante l'ID dell'hackathon associato al team
     */
    public String getIdHackathon() {return idHackathon;}

    /**
     * Restituisce il voto attuale assegnato al team.
     *
     * @return un {@code float} rappresentante il voto del team
     */
    public float getVoto() {return voto;}

    /**
     * Imposta o aggiorna il voto del team.
     *
     * @param voto nuovo valore del voto da assegnare al team
     */
    public void setVoto(float voto) {this.voto = voto;}

    /**
     * Restituisce la lista dei partecipanti appartenenti al team.
     *
     * @return un {@link ArrayList} contenente gli oggetti {@link Partecipante} del team
     */
    public ArrayList<Partecipante> getArrayPartecipante() {return arrayPartecipante;}


    /**
     * Aggiunge un partecipante al team se non è stato raggiunto il numero massimo di membri.
     * Se il team ha già raggiunto il limite {@code MAX_MEMBRI}, viene generata un'eccezione.
     *
     * @param partec oggetto {@link Partecipante} da aggiungere al team
     * @throws IllegalArgumentException se il team ha già raggiunto il numero massimo di membri
     */
    public void addPartecipante(Partecipante partec) throws IllegalArgumentException{

        if(numeroMembri < MAX_MEMBRI)
        {
            this.arrayPartecipante.add(partec);
            numeroMembri++;
        }

        else
            throw new IllegalArgumentException("Il team scelto ha raggiunto il numero massimo di membri");
    }

    /**
     * Crea un nuovo oggetto {@link Progresso} associato al team e lo aggiunge
     * alla lista dei progressi del team.
     *
     * @param nomeProgresso   il nome del progresso da creare
     * @param codiceProgresso il codice identificativo del progresso
     * @return l'oggetto {@link Progresso} appena creato e aggiunto al team
     */
    public Progresso caricaProgresso(String nomeProgresso,String codiceProgresso)
    {

        Progresso progresso =new Progresso();
        progresso.setCodiceProgresso(codiceProgresso);
        progresso.setNome(nomeProgresso);
        progresso.setIdTeam(this.getId());
        this.arrayProgresso.add(progresso);
        return progresso;

    }

    /**
     * Restituisce la lista dei progressi associati al team.
     *
     * @return un {@link ArrayList} contenente gli oggetti {@link Progresso} del team
     */
    public ArrayList<Progresso> getArrayProgresso() {return arrayProgresso;}


    /**
     * Genera un identificativo univoco per un nuovo team.
     * L'ID è costruito concatenando il prefisso {@code "T"} con il valore
     * corrente del contatore {@code nT}, formattato con zeri iniziali
     * per ottenere sempre una stringa di almeno tre cifre. Dopo la generazione,
     * il contatore {@code nT} viene incrementato di 1.
     *
     * @return una {@link String} rappresentante l'identificativo univoco del team
     */
    private String creaIDTeam(){

        String idCodice ="-1";
        if(nT >=0 && nT <10)  idCodice ="T00"+nT;

        else if (nT <100)   idCodice ="T0"+nT;

        else if (nT <1000)   idCodice ="T"+nT;

        nT++;

        return idCodice;

    }

    /**
     * Restituisce il valore corrente del contatore {@code nT}.
     * Questa variabile statica rappresenta il numero progressivo
     * utilizzato per la generazione degli identificativi dei team.
     *
     * @return il valore corrente di {@code nT}
     */
    public static int getnT() {
        return nT;
    }


    /**
     * Imposta un nuovo valore per il contatore {@code nT}.
     * Questa variabile statica viene utilizzata per la generazione
     * degli identificativi progressivi dei team.
     *
     * @param nT nuovo valore da assegnare a {@code nT}
     */
    public static void setnT(int nT) {
        Team.nT = nT;
    }


    /**
     * Genera un codice di accesso univoco per il team.
     * Il codice ha lunghezza 7 e segue il formato:
     * - lettere maiuscole nelle posizioni 0, 1, 5, 6
     * - numeri nelle posizioni 2, 3, 4
     *
     * @return una {@link String} rappresentante il codice di accesso generato
     */
    private String creaCodiceAccesso(){
        char[] lettere={'A','B','C','D','E','F','G','H','I','L','J','K','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] numeri={'0','1','2','3','4','5','6','7','8','9'};


        StringBuilder codiceAccesso= new StringBuilder();
        Random random=new Random();

        for(int i=0;i<7;i++)
        {
            if(i<2 || i>4)
                codiceAccesso.append(lettere[random.nextInt(0, lettere.length)]);
            else
                codiceAccesso.append(numeri[random.nextInt(0, numeri.length)]);
        }

        return codiceAccesso.toString();
    }







}
