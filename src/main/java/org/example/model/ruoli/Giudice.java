package org.example.model.ruoli;


public class Giudice extends UtenteRegistrato
{
    private static int nG =0;
    private String iDHackatlon;

    /**
     * Costruttore di giudice che prende in input: nome, cognome, email, password e ID dell'hackathon a cui appartiene, per
     * assegnarli rispettivamente ai suoi attributi. Inserendo per l'ID del giudice il risultato della funzione che li crea
     * autonomamente {@link #codiceID()}
     * @param nome del giudice
     * @param cognome del giudice
     * @param email del giudice
     * @param password del giudice
     * @param iDHackatlon a cui appartiene il giudice
     */
    public Giudice(String nome,String cognome,String email,String password,String iDHackatlon){ //Non uso super perche poi potrebbe avanzare con la creazione di indirizzi ID di Utenete registrato
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.iDHackatlon = iDHackatlon;
        this.id = codiceID();}

    /**
     *Costruttore di giudice che prende in input: ID del giudice, nome, cognome, email, password e ID dell'hackathon a cui appartiene, per
     * assegnarli rispettivamente ai suoi attributi.
     * @param id del giudice
     * @param nome del giudice
     * @param cognome del giudice
     * @param email del giudice
     * @param password del giudice
     * @param iDHackatlon a cui appartiene il giudice
     */
    public Giudice(String id, String nome, String cognome, String email, String password, String iDHackatlon){

        this.id = id;   this.nome=nome;   this.cognome=cognome;   this.email=email;   this.password=password;
        this.iDHackatlon = iDHackatlon;
}

    /**
     * Restituisce l id dell'hackathon a cui appartiene il giudice
     * @return id dell'hackathon a cui appartiene il giudice
     */
    public String getiDHackatlon() {return iDHackatlon;}





    /**
     * Inserisce un voto ad un team e verifica che questo sia compreso tra 0 e 10, in caso contrario lancia un eccezione
     * @param team riferimento al team a cui assegnare il voto
     * @param votoStr voto sottoforma di stringa da assegnare al team
     * @throws IllegalArgumentException nel caso in cui il voto non sia compreso tra 0 e 10
     */
    public void valutaTeam(Team team, String votoStr) throws IllegalArgumentException
    {

       try {
           team.setVoto(Float.parseFloat(votoStr));//converto la stringa in float
           if (team.getVoto() < 0 || team.getVoto()>10)
               throw new IllegalArgumentException();
       } catch (Exception _) {
           throw new IllegalArgumentException("Valore inserito non valido\n(Deve essere compreso tra 0 e 10)");
       }

    }

    /**
     * Algoritmo che crea un codice ID personalizzato per i giudici, un modello formato dalla prima lettera G e una serie
     * di zeri fino alla cifra più significativa. Ogni codice e relazionato all'ordine in cui viene creata un istanza
     * @return il codice ID creato
     */
    private String codiceID(){
        String idCodice ="-1";
        if(nG >=0 && nG <10)  idCodice ="G00"+nG;

        else if (nG <100)   idCodice ="G0"+nG;

        else if (nG <1000)   idCodice ="G"+nG;

        nG++;

        return idCodice;
    }

    /**
     * Restituisce la variabile per la generazione del ID
     * @return la variabile per la generazione del ID
     */
    public static int getnG() {
        return nG;
    }

    /**
     * Utilizzata per assegnare un valore alla variabile per la generazione del ID
     * @param nG valore a cui viene settata la variabile per il codice ID
     */
    public static void setnG(int nG) {
        Giudice.nG = nG;
    }
}
