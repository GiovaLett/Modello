package org.example.model.ruoli;
import org.example.model.Hackathon;
import org.example.model.Piattaforma;

public class Organizzatore extends UtenteRegistrato
{


    /**
     * Costruttore della classe {@code Organizzatore}.
     * Inizializza un nuovo oggetto organizzatore con i dati personali e di accesso forniti.
     *
     * @param id identificativo univoco dell'organizzatore
     * @param nome nome dell'organizzatore
     * @param cognome cognome dell'organizzatore
     * @param email indirizzo email dell'organizzatore
     * @param password password associata all'account dell'organizzatore
     */
    public Organizzatore(String id, String nome, String cognome, String email, String password)
    {
        this.id = id;  this.nome=nome;  this.cognome=cognome;
        this.email=email; this.password=password;
    }

    /**
     * Apre le iscrizioni sulla piattaforma, abilitando la possibilità
     * per gli utenti di registrarsi a un hackathon.
     *
     * @param piattaforma oggetto {@link Piattaforma} su cui abilitare le iscrizioni
     */
    public void apriIscrizioni(Piattaforma piattaforma) {piattaforma.setOpenIscr(true);}

    /**
     * Chiude le iscrizioni sulla piattaforma e imposta lo stato dell'evento come pronto.
     * Dopo l'esecuzione di questo metodo, non sarà più possibile registrarsi,
     * e la piattaforma sarà segnalata come pronta per l'inizio dell'hackathon.
     *
     * @param piattaforma oggetto {@link Piattaforma} su cui aggiornare lo stato delle iscrizioni e dell'evento
     */
    public void chiudiIscrizioni(Piattaforma piattaforma) {
        piattaforma.setOpenIscr(false);
        piattaforma.setEventoPronto(true);

    }


    /**
     * Converte un utente registrato in un giudice per un determinato hackathon.
     * Viene creato un nuovo oggetto {@link Giudice} a partire dai dati dell'utente
     * e aggiunto alla lista dei giudici dell'hackathon. Successivamente,
     * l'utente viene sostituito dal giudice nella lista degli utenti registrati
     * della piattaforma.
     *
     * @param utente     l'utente registrato da promuovere a giudice
     * @param piattaforma la piattaforma su cui avviene la sostituzione dell'utente con il giudice
     * @param hackathon   l'hackathon a cui associare il nuovo giudice
     */
    public void SelezionaGiudice(UtenteRegistrato utente, Piattaforma piattaforma, Hackathon hackathon)
    {
        Giudice nuovoGiudice = new Giudice(utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getPassword(), hackathon.getId());
        hackathon.addGiudice(nuovoGiudice);
        piattaforma.getListaUtenReg().remove(utente);
        piattaforma.getListaUtenReg().add(nuovoGiudice);

    }


    /**
     * Rimuove un giudice da un hackathon e lo riconverte in un utente registrato.
     * Il giudice viene eliminato dalla lista dei giudici dell'hackathon e
     * sostituito nella lista degli utenti registrati della piattaforma
     * con un nuovo oggetto {@link UtenteRegistrato} creato a partire dai suoi dati.
     *
     * @param giudice     il giudice da rimuovere e riconvertire in utente registrato
     * @param piattaforma la piattaforma in cui aggiornare la lista degli utenti registrati
     * @param hackathon   l'hackathon da cui rimuovere il giudice
     * @return un nuovo oggetto {@link UtenteRegistrato} che rappresenta il giudice riconvertito
     */
    public UtenteRegistrato rimuoviGiudice(Giudice giudice, Piattaforma piattaforma, Hackathon hackathon)
    {
        UtenteRegistrato utente=new UtenteRegistrato(giudice.getNome(),giudice.getCognome(),giudice.getEmail(), giudice.getPassword());
        piattaforma.getListaUtenReg().add(utente);
        piattaforma.getListaUtenReg().remove(giudice);
        hackathon.getListaGiudici().remove(giudice);
        return utente;
    }


    /**
     * Crea un nuovo hackathon e lo aggiunge alla piattaforma.
     * L'hackathon viene inizializzato con un nome, una sede e una durata
     * e poi registrato nella lista degli hackathon della piattaforma.
     *
     * @param piattaforma     la piattaforma su cui registrare il nuovo hackathon
     * @param nomeHackathon   il nome dell'hackathon da creare
     * @param sede            la sede in cui si svolgerà l'hackathon
     * @param durata          la durata dell'hackathon espressa in giorni
     * @return l'oggetto {@link Hackathon} appena creato e aggiunto alla piattaforma
     */
    public Hackathon creaHackathon(Piattaforma piattaforma, String nomeHackathon,String sede, int durata){
        Hackathon nuovoHackathon=new Hackathon(nomeHackathon);
        nuovoHackathon.setSede(sede);
        nuovoHackathon.setDurata(durata);
        piattaforma.addHackathon(nuovoHackathon);
        return nuovoHackathon;
    }
}
