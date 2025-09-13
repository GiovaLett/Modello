package org.example.model.ruoli;

public class Utente
{
    String nome;
    String cognome;


    /**
     * Costruttore vuoto della classe {@code Utente}.
     * Crea un oggetto {@code Utente} senza inizializzare i campi.
     */
    public Utente(){}


    /**
     * Costruttore della classe {@code Utente}.
     * Inizializza un nuovo utente impostando il nome e il cognome forniti.
     *
     * @param nome    nome dell'utente
     * @param cognome cognome dell'utente
     */
    public Utente(String nome, String cognome)
    {
        this.nome=nome;
        this.cognome=cognome;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return una {@link String} contenente il nome dell'utente
     */
    public String getNome() {return nome;}

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return una {@link String} contenente il cognome dell'utente
     */
    public String getCognome() {return cognome;}



}
