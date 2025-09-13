package org.example.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HackathonDao {


    //UTENTI REGISTRATI

    /**
     * Utilizzata per scaricare le informazioni di tutti gli utenti registrati alla piattaforma
     * @param id ArrayList che verrà riempito con gli id di tutti gli utenti registrati
     * @param nome ArrayList che verrà riempito con i nomi di tutti gli utenti registrati
     * @param cognome ArrayList che verrà riempito con i cognomi di tutti gli utenti registrati
     * @param email ArrayList che verrà riempito con le email di tutti gli utenti registrati
     * @param password ArrayList che verrà riempito con le password di tutti gli utenti registrati
     * @param idHackathon ArrayList che verrà riempito con gli id dell' hackathon a cui appartengono tutti gli utenti registrati
     * @param idTeam ArrayList che verrà riempito con gli id del Team a cui appartengono tutti gli utenti registrati
     * @throws SQLException nel caso in cui l'operazione dia problemi con il DB
     */
    void getAllUtentiRegistratiDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<String> cognome, ArrayList<String> email,
                                  ArrayList<String> password, ArrayList<String> idHackathon, ArrayList<String> idTeam) throws SQLException;

    /**
     * Inseriece all'interno del DB un nuovo utente con i corrispettivi dati
     * @param id del nuovo utente inserito
     * @param nome del nuovo utente inserito
     * @param cognome del nuovo utente inserito
     * @param email del nuovo utente inserito
     * @param password del nuovo utente inserito
     * @throws SQLException nel caso in cui l'operazione dia origine a errori
     */
    void addUtenteRegistratoDB(String id,String nome, String cognome, String email, String password) throws SQLException;

    /**
     * Converte un generico utente a giudice
     * @param idUtente codice ID dell'utente da modificare, utilizzato per trovarlo all'interno del DB
     * @param idGiudice nuovo codice ID che acquisirà l'utente, il modello di quello di un giudice
     * @param idHackathon il codice ID dell hackathon di cui l'utente sarà giudice
     * @throws SQLException nel caso in cui la modifica dell'utente non andrà a buon fine
     */
    void setUtenteToGiudiceDB(String idUtente,String idGiudice,String idHackathon)throws SQLException;

    /**
     * Trasforma un giudice in utente generico
     * @param idUtente il nuovo codice ID che assumerà l'utente
     * @param idGiudice il codice ID del giudice che verrà modificato, utilizzato per trovarlo nel DB
     * @throws SQLException nel caso in cui tale modifiche sollevino eccezioni
     */
    void setGiudiceToUtenteDB(String idUtente,String idGiudice)throws SQLException;

    /**
     * Converte un generico utente a partecipante
     * @param idUtente codice ID dell'utente che diventerà partecipante
     * @param idPartecipante nuovo codice ID che assumerà l'utente una volta divenuto partecipante
     * @param idHackathon codice ID dell hackathon a cui parteciperà l'utente
     * @param idTeam codice ID del Team di cui l'utente sarà membro
     * @throws SQLException nel caso in cui tale modifiche sollevino eccezioni
     */
    void setUtenteToPartecipanteDB(String idUtente,String idPartecipante,String idHackathon, String idTeam)throws SQLException;


    //TEAMS

    /**
     * Estrae dal DB i dati relativi a tutti i Team
     * @param id ArrayList che contiene al suo interno i codici ID di tutti i teams
     * @param nome ArrayList che contiene al suo interno i nomi di tutti i teams
     * @param codAccesso ArrayList che contiene al suo interno i codici d' accesso di tutti i teams
     * @param voti ArrayList che contiene al suo interno i voti assegnati a tutti i teams
     * @param idHackathon ArrayList che contiene al suo interno i codici ID dell hackathon a cui partecipano i teams
     * @throws SQLException nel caso in cui tale estrazione sollevi delle eccezioni
     */
    void getAllTeamsDB(ArrayList<String> id,ArrayList<String> nome, ArrayList<String> codAccesso
            , ArrayList<Float> voti, ArrayList<String> idHackathon) throws SQLException;

    /**
     * Aggiunge al DB un Team con le relative informazioni
     * @param id codice ID del Team
     * @param nome nome del Team
     * @param codAccesso codice accesso per essere membro del Team
     * @param idHackathon codice ID del Hackathon a cui partecipa il Team
     * @throws SQLException Nel caso in cui tale inserimento nel DB sollevi eccezioni
     */
    void addTeamsDB(String id, String nome, String codAccesso, String idHackathon) throws SQLException;

    /**
     * Memorizza nel DB il voto assegnato a un Team specifico
     * @param idTeam codice ID del Team a cui deve essere assegnato il voto
     * @param voto voto che viene assegnato al Team, numero decimale
     * @throws SQLException nel caso in cui tale inserimento sollevi eccezioni
     */
    void setVotoTeam(String idTeam, float voto)throws SQLException;

    /**
     * Salva nel DB un commento relativo a un determinato progresso
     * @param idTeam id del team a cui appartiene tale progresso
     * @param nomeProgresso nome del progresso a cui si vuole allegare il commento
     * @param commento Stringa che descrive il progresso
     * @throws SQLException nel caso in cui tale operazione sollevi eccezioni
     */
    void setCommentoProgresso(String idTeam,String nomeProgresso, String commento) throws SQLException;



    //PROGRESSI

    /**
     * Estrae i progressi di un determinato Team con il suo nome, codice e commento
     * @param nome del progresso da estrarre
     * @param codice codice del programma scritto che rappresenta il progresso di per se
     * @param commento commento dei giudici relativo a tale progresso
     * @param idTeam codice ID del team a cui appartiene tale progresso
     * @throws SQLException Nel caso in cui tale estrazione sollevi delle eccezioni
     */
    void getProgressiTeam(ArrayList<String> nome,ArrayList<String> codice, ArrayList<String> commento,
                          String idTeam) throws SQLException;

    /**
     * Aggiunge un progresso di un determinato Teamal DB
     * @param idTeam codice ID del Team a cui appartiene tale progresso
     * @param nomeProgresso nome del progresso aggiunto
     * @param codice codice del programma (rappresenta il progresso)
     * @throws SQLException nel caso in cui tale inserimento sollevi delle eccezioni
     */
    void addProgresso(String idTeam,String nomeProgresso, String codice) throws SQLException;


    //HACKATHON

    /**
     * Aggiunge un hackathon al DB con i relativi dati
     * @param id codice ID dell'hackathon
     * @param nome nome dell'hackathon
     * @param sede luogo in cui avrà luogo l'hackethon
     * @param durata durata dell'hackethon
     * @throws SQLException nel caso in cui l'inserimento nel DB sollevi eccezioni
     */
    void  addHackathonDB(String id,String nome,String sede, int durata) throws SQLException;

    /**
     * Rimuove un hackathon dal DB conoscendone il codice ID
     * @param id codice ID dell hackathon da rimuovere
     * @throws SQLException nel caso in cui la rimozione sollevi delle eccezioni
     */
    void removeHackathonDB(String id) throws SQLException;

    /**
     * Estrae tutti gli hackathon dal DB con le relative informazioni, dove ogni dato è inserito in un ArrayList, lo stesso indice
     * per ogni ArrayList fa riferimento al medesimo hackathon
     * @param id ArrayList contenente: codice ID dell Hackathon
     * @param nome ArrayList contenente: nome dell Hackathon
     * @param nMaxPartec ArrayList contenente: numero massimi di partecipanti all hackathon
     * @param numTeam ArrayList contenente: numero di team che presenta l hackathon
     * @param problema ArrayList contenente: problema da risolvere
     * @param teamSuff ArrayList contenente: flag che indice se i team sono maggiori di 2 (TRUE) o minori (FALSE)
     * @param viewProblema ArrayList contenente: flag che indica se la visibilità del problema è disponibile ai partecipanti (TRUE) altrimenti (FALSE)
     * @param eventoFinito ArrayList contenente: flag che indica sei l hackathon si è concluso (TRUE) altrimenti (FALSE)
     * @param votazConclusa ArrayList contenente: flag che indica se i giudici hanno concluso la votazione (TRUE) altrimenti (FALSE)
     * @param classPubblicata ArrayList contenente: flag che indica se la classifica è stata pubblicata (TRUE) altrimenti (FALSE)
     * @param sede ArrayList contenente: la sede in cui si terrà l evento
     * @param durata ArrayList contenente: la durata dell'evento
     * @throws SQLException nel caso in cui l'estrazione sollevi delle eccezioni
     */
    void getAllHackathonDB(ArrayList<String> id, ArrayList<String> nome, ArrayList<Integer> nMaxPartec, ArrayList<Integer> numTeam, ArrayList<String> problema,
                                  ArrayList<Boolean> teamSuff, ArrayList<Boolean> viewProblema, ArrayList<Boolean> eventoFinito, ArrayList<Boolean> votazConclusa,
                                  ArrayList<Boolean> classPubblicata, ArrayList<String> sede, ArrayList<Integer> durata) throws SQLException;

    /**
     * Salva nel DB il problema relativo a uno specifico hackathon
     * @param idHackathon codice ID dell hackathon a cui appartiene il problema
     * @param problema problema che deve essere memorizzato
     * @throws SQLException nel caso in cui l'operazione sollevi eccezioni
     */
    void setHackathonProblema(String idHackathon,String problema) throws SQLException;


    //FLAGS HACKATHON

    /**
     * Imposta il valore della flag teamSuff in un determinato Hackathon
     * @param teamSuff la flag -valore booleano- che deve essere memorizzata
     * @param idHackathon codice ID dell hackathon a cui deve essere memorizzato tale flag
     * @throws SQLException nel caso in cui l'operazione sollevi delle eccezioni
     */
    void setTeamSuff(boolean teamSuff, String idHackathon)throws SQLException;

    /**
     * Imposta il valore della flag viewProblema in un determinato Hackathon
     * @param viewProblema la flag -valore booleano- che deve essere memorizzata
     * @param idHackathon codice ID dell hackathon a cui deve essere memorizzato tale flag
     * @throws SQLException nel caso in cui l'operazione sollevi delle eccezioni
     */
    void setViewProblema(boolean viewProblema,String idHackathon)throws SQLException;

    /**
     * Imposta la flag eventoFinito di un determinato Hackathon in TRUE o FALSE
     * @param eventoFinito Valore booleano da assegnare alla flag
     * @param idHackathon codice ID dell' hackathon a cui si vuole modificare tale flag
     * @throws SQLException nel caso in cui la modifica della flag generi Eccezioni
     */
    void setEventoFinito(boolean eventoFinito,String idHackathon)throws SQLException;

    /**
     *  Imposta la flag votazConclusa di un determinato Hackathon in TRUE o FALSE
     * @param votazConclusa Valore booleano da assegnare alla flag
     * @param idHackathon codice ID dell' hackathon a cui si vuole modificare tale flag
     * @throws SQLException nel caso in cui la modifica della flag generi Eccezioni
     */
    void setVotazConclusa(boolean votazConclusa, String idHackathon)throws SQLException;

    /**
     * Imposta la flag classPubblicata di un determinato Hackathon in TRUE o FALSE
     * @param classPubblicata Valore booleano da assegnare alla flag
     * @param idHackathon codice ID dell' hackathon a cui si vuole modificare tale flag
     * @throws SQLException nel caso in cui la modifica della flag generi Eccezioni
     */
    void setClassPubblicata(boolean classPubblicata, String idHackathon)throws SQLException;


    //FLAGS PIATTAFORMA

    /**
     * Estrae dal DB le flag che possiede la piattaforma ("openIscr" ed "eventoPronto")
     * @return un array di valori booleani contenente al primo indice la flag openIscr
     * e al secondo la flag evento pronto
     * @throws SQLException nel caso in cui l'estrazione di tali valori dal DB sollevi eccezioni
     */
    boolean[] getAllFlagsPiattaforma()throws SQLException;

    /**
     * Imposta la flag "openIscr" della piattaforma in TRUE o FALSE
     * @param openIscr Valore booleano da assegnare alla flag
     * @throws SQLException nel caso in cui la modifica della flag generi Eccezioni
     */
    void setOpenIscr(boolean openIscr)throws SQLException;

    /**
     * Imposta la flag "eventoPronto" della piattaforma in TRUE o FALSE
     * @param eventoPronto Valore booleano da assegnare alla flag
     * @throws SQLException nel caso in cui la modifica della flag generi Eccezioni
     */
    void setEventoPronto(boolean eventoPronto)throws SQLException;

    //RICHIESTE GIUDICE

    /**
     * Memorizza all'interno del DB una richiesta di essere giudice riferita a un determinato utente
     * @param idUtente codice ID dell'utente a cui è stata mendata le richiesta
     * @param idHackathon codice ID dell' hackathon per la quale è stato richiesto di essere giudice
     * @param richiesta Stringa che rappresenta la richiesta
     * @throws SQLException nel caso in cui la memorizzazione nel DB generi eccezioni
     */
    void salvaRichiestaDB(String idUtente,String idHackathon, String richiesta)throws SQLException;

    /**
     * Rimuove dal DB una richiesta per essere giudice di un determinato utente
     * @param idUtente codice ID dell'utente di cui si vuole rimuovere la richiesta
     * @throws SQLException nel caso in cui la rimozione dal DB generi delle eccezioni
     */
    void eliminaRichiestaGiudice(String idUtente) throws SQLException;

    /**
     * Estrae dal DB tutte le richieste per essere giudice di tutti gli utenti che ne posseggono
     * @param idUtente ArrayList che contiene i codici ID di tutti gli utenti che possiedono una richiesta per essere giudice
     * @param richieste ArrayList di stringhe che rappresento esplicitamente una richiesta per essere giudice
     * @param idHackathon ArrayList che contiene i codici ID degli Hackathon per i quali è stata inviata una richiesta
     * @throws SQLException nel caso in cui l'estrazione dal DB sollevi delle eccezioni
     */
    void getAllRichiesteGiudiciDB(ArrayList<String> idUtente, ArrayList<String> richieste, ArrayList<String> idHackathon) throws SQLException;


    //COSTANTI ID

    /**
     * Estrae il valore delle costanti utilizzate per creare dei codici ID personalizzati in base al ruolo dell'utente
     * @return un array contenente in ordine le costanti: nH, nT, nU, nP, nG;
     * che fanno rispettivamente riferimento a: Hackathon, Team, Utente, Partecipante, Giudice
     * @throws SQLException nel caso in cui l'estrazione dal DB sollevi delle eccezioni
     */
    int[] getCostantiID() throws SQLException;

    /**
     * Memorizza il valore della costante nH nel DB
     * @param nH valore da memorizzare
     * @throws SQLException nel caso in cui la memorizzazione nel DB sollevi eccezioni
     */
    void storeNH(int nH) throws SQLException;

    /**
     * Memorizza il valore della costante nU nel DB
     * @param nU valore da memorizzare
     * @throws SQLException nel caso in cui la memorizzazione nel DB sollevi eccezioni
     */
    void storeNU(int nU) throws SQLException;

    /**
     * Memorizza il valore della costante nG nel DB
     * @param nG valore da memorizzare
     * @throws SQLException nel caso in cui la memorizzazione nel DB sollevi eccezioni
     */
    void storeNG(int nG) throws SQLException;

    /**
     * Memorizza il valore della costante nP nel DB
     * @param nP valore da memorizzare
     * @throws SQLException nel caso in cui la memorizzazione nel DB sollevi eccezioni
     */
    void storeNP(int nP) throws SQLException;

    /**
     * Memorizza il valore della costante nT nel DB
     * @param nT valore da memorizzare
     * @throws SQLException nel caso in cui la memorizzazione nel DB sollevi eccezioni
     */
    void storeNT(int nT) throws SQLException;









}

