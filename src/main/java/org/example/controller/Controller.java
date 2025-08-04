package org.example.controller;

import org.example.implementazionePostgresDAO.HackathonDAOImplement;
import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;
import org.example.Model.Progresso;
import org.example.Model.ruoli.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class Controller
{
    //per stabilire se scaricare o meno gli utenti registrati dal database


    /**
     * L'attributo organizzatore fa rimerimento ad' un istanza di {@link Organizzatore}
     * La quale gestisce e organizza gli hackathon
     */
    Utente_registrato organizzatorePiatt =new Organizzatore("0000","Giovanni","Lettieri","gio@email.it","password");
    Piattaforma piattaforma=new Piattaforma();

    Utente_registrato utenteCorrente;
    Hackathon hackathonCorrente;
    Team teamCorrente;
    Progresso progressoCorrente;

    Utente_registrato utenteSelezionato;
    Giudice giudiceSelezionato;

    /**
     * Il costruttore Controller aggiunge automaticamente l'organizzatore alla piattaforma
     */
    public Controller(){

        piattaforma.addUtenteReg(organizzatorePiatt);
    }


    /**
     * Richiama la funzione di {@link Piattaforma} che restuisce una flag
     * la quale indica se l'evento è pronto o meno
     * @return valore booleano che indica se l 'evento è pronto
     */
    public boolean isEventoPronto() {return piattaforma.isEventoPronto();}

    /**
     * Richiama la funzione di {@link Piattaforma} che restuisce una flag
     *  la quale indica se le iscrizioni sono aperte
     *  @return valore booleano che indica se le iscrizioni sono aperte
     */
    public boolean isOpenIscri(){return piattaforma.isOpen_iscr();}


    public ArrayList<Hackathon> getListaHackathon(){return piattaforma.getListaHackathon();}

    /**
     * Funzione che ritorna la data dell'evento nel formato: anno-mese-giorno
     * @return una stringa che rappresenta la data dell'evento
     */
    public String getDataEvento(){
        if(piattaforma.getDataEvento()==null)
            return null;
        else
            return piattaforma.getDataEvento().toString();
    }

    /**
     * Verifica se la durata dell'hackathon è terminata, confrontando il suo giorno finale con il giorno corrente
     * @return TRUE se è passato il giorno finale, FALSE in caso contrario
     */
    public boolean isDurataHCTerminata() {

        LocalDate oggi=LocalDate.now();
        LocalDate giornoFinaleHC= piattaforma.getDataEvento().plusDays( hackathonCorrente.getDurata() );

        if(oggi.isBefore( giornoFinaleHC ))
            return false;
        else if(hackathonCorrente.isEventoFinito()){
            return true;
        }
        else{
            try {
                setEventoFinito(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    /**
     * Calcola la data di scadenza iscrizioni rispetto alla data dell'evento (2 giorni prima)
     * e la restituisce sottoforma di stringa
     * @return una stringa che indica la data di scadenza iscrizioni
     */
    public String getDataScadenzaIscriz(){
        LocalDate scadenzaData=piattaforma.getDataEvento().minusDays(2);
        return scadenzaData.toString();

    }


    /**
     * Valuta se vi sono Hackaton registrati in piattaforma
     * @return
     */
    public boolean isListaHackathonEmpty(){  return piattaforma.getListaHackathon().isEmpty();}

    /**
     * Confronta la data odierna rispetto a quella in cui scadono le iscrizioni.
     * Se tale data viene superata allora vengono chiuse le iscrizioni
     * impostanto le 2 flag di {@link Piattaforma}
     * @return valore booleano che indica se si è superata la data in cui scadono le iscrizioni
     */
    public boolean isIscrizioneScaduta(){

        if(piattaforma.getDataEvento()==null)
            return false;

        LocalDate oggiData=LocalDate.now();
        LocalDate dataFineIscriz=piattaforma.getDataEvento().minusDays(2);

        if(oggiData.isBefore(dataFineIscriz))
            return false;

        else{

            piattaforma.setOpen_iscr(false);
            piattaforma.setEventoPronto(true);
            try{
            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
                hackathonDAOImplement.setOpenIscr(false);
                hackathonDAOImplement.setEventoPronto(true);
            }catch (SQLException exc){exc.printStackTrace();}
            return true;
        }

    }







      //SET UTENTI

    /**
     * Restituisce il nome dell Utente Corrente
     * @return Stringa che indica il nome dell' utente corrente
     */
    public String getNomeUtenCorr(){return utenteCorrente.getNome();}

    /**
     * Restituisce il cognome dell Utente Corrente
     * @return Stringa che indica il cognome dell' utente corrente
     */
    public String getCognomeUtenCorr(){return utenteCorrente.getCognome();}

    /**
     * Restituisce l' ID dell Utente Corrente
     * @return Stringa che indica l'ID dell' utente corrente
     */
    public String getIdUtenCorr(){return utenteCorrente.getID();}

    /**
     * Restituisce la richiesta per essere giudice dell Utente Corrente
     * @return Stringa che rappresenta la richiesta per essere giudice dell Utente Corrente
     */
    public String getRichiestaUC(){return utenteCorrente.getRichiestaGiudice();}


    /**
     * Funzione che ha come parametro un {@link Utente_registrato} il quale verrà
     * impostato come utente corrente della piattaforma.
     * Utilizzato in seguito al riconoscimento dell'utente nella fase di accesso
     * @param utenteCorrente che diventerà utente corrente
     */
    private void setUtenteCorrente(Utente_registrato utenteCorrente) {
        this.utenteCorrente = utenteCorrente;
    }


    /**
     * Prende in ingresso le informazioni di un utente e lo aggiunge alla piattaforma
     * @param nome dell'utente
     * @param cognome dell'utente
     * @param email dell'utente
     * @param password dell'utente
     * @throws SQLException nel caso ci siano problemi nell'aggiunta dell'utente al Data Base
     */
    public void addUtenteRegistrato(String nome,String cognome,String email,String password) throws SQLException
    {
        Utente_registrato nuovoUtente =new Utente_registrato(nome,cognome,email,password);

        try {
            HackathonDAOImplement hackathonDAOImplement = new HackathonDAOImplement();
            hackathonDAOImplement.addUtenteRegistratoDB(nuovoUtente.getID(), nuovoUtente.getNome(), nuovoUtente.getCognome(), nuovoUtente.getEmail(), nuovoUtente.getPassword());
            piattaforma.addUtenteReg(nuovoUtente);

            hackathonDAOImplement.storeNU(Utente_registrato.getnU());
        }catch (SQLException ex){
            ex.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Valuta se una determinata email è gia presente nella piattaforma
     * @param email che si vuole verificare
     * @return valore booleano: true se già registrata; false se non lo è
     */
    public boolean isEmailRegisteredYet(String email){

        for(Utente_registrato utente: piattaforma.getListaUtenReg()){
            if(utente.getEmail().equals(email))
                return true;
        }
        return false;
    }

    /**
     * Cerca un utente registrato alla piattaforma avendo in ingresso l'email e la password di esso
     * @param email dell'utente da cercare
     * @param password dell'utente da cercare
     * @return L'istanza di {@link Utente_registrato} che indica l'utente che cercavamo
     * @throws IllegalArgumentException nel caso in cui non viene trovato
     */
    private Utente_registrato findAccount(String email, String password) throws IllegalArgumentException{

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getEmail().equals(email) && utente.getPassword().equals(password))
                return utente;
        }
        throw new IllegalArgumentException("Credenziali errate");
    }

    /**
     *Scarica i valori relativi agli utenti registrati presenti nel data base e
     *in base al valore degli ID Hackathon e ID Team di ognuno li smista:
     * -ID Hackathon != null e ID Team==null viene istanziato un {@link Giudice};
     * -ID Hackathon != null e ID Team!=null viene istanziato un {@link Partecipante};
     * -altrimenti viene istanziato un {@link Utente_registrato}
     * @throws SQLException nel caso vi sia un errore con il data base;
     */
    public void getUtentiRegistratiDB()throws SQLException{

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        ArrayList<String>id=new ArrayList<>();
        ArrayList<String>nome=new ArrayList<>();
        ArrayList<String>cognome=new ArrayList<>();
        ArrayList<String>email=new ArrayList<>();
        ArrayList<String>password=new ArrayList<>();
        ArrayList<String>idHackathon=new ArrayList<>();
        ArrayList<String>idTeam=new ArrayList<>();

        hackathonDAOImplement.getAllUtentiRegistratiDB(id, nome, cognome, email, password, idHackathon,idTeam);


        for(int i=0;i<id.size();i++)
        {
            System.out.println(email.get(i)+"   idHack: "+idHackathon.get(i)+"   idTeam: "+idTeam.get(i));

            if(idHackathon.get(i)==null && idTeam.get(i)==null) {
                piattaforma.getListaUtenReg().add(
                        new Utente_registrato(id.get(i), nome.get(i), cognome.get(i), email.get(i), password.get(i)));
            }
            else if(idHackathon.get(i)!=null && idTeam.get(i)==null) {

                Giudice giudice=new Giudice(id.get(i), nome.get(i), cognome.get(i), email.get(i), password.get(i), idHackathon.get(i));
                piattaforma.getListaUtenReg().add(giudice);

                Hackathon hackathon=findHackId(giudice.getIDHackatlon());
                hackathon.addGiudice(giudice);

            }
            else {
                Partecipante partecipante= new Partecipante(id.get(i), nome.get(i), cognome.get(i), email.get(i), password.get(i), idTeam.get(i),idHackathon.get(i));
                piattaforma.getListaUtenReg().add(partecipante);

                Hackathon hackathon=findHackId(partecipante.getIDHackathon());
                Team team=findIDTeam(partecipante.getIDTeam(), hackathon);
                team.addPartecipante(partecipante);
                hackathon.incrementaNpartec();
            }
        }

        ArrayList<String> idUtente=new ArrayList<>();
        ArrayList<String> richiesta=new ArrayList<>();
        ArrayList<String> idHack=new ArrayList<>();

        hackathonDAOImplement.getAllRichiesteGiudiciDB(idUtente,richiesta,idHack);

        for(int i=0; i<idUtente.size(); i++){
            Utente_registrato utente=findIdUtenteReg(idUtente.get(i));
            if(utente!=null){
                utente.setRichiestaGiudice(richiesta.get(i));
                utente.setHackIDRichiesta(idHack.get(i));
            }
        }
    }

    /**
     * Cerca un utente registrato alla piattaforma avendo in ingresso il suo ID
     * @param idUtente dell'utente da cercare
     * @return il riferimento a quell'utente
     */
    private Utente_registrato findIdUtenteReg(String idUtente){

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getID().equals(idUtente))
                return utente;
        }
        return null;
    }

    /**
     * Cerca un utente della piattaforma avendo in ingresso email e password,
     * utilizzando la funzione {@link #findAccount(String, String)}.
     * <p>
     * Viene poi valutato a che classe appartiene l'utente trovato:
     * <p>
     * - {@link Organizzatore}, viene restituito 0;
     * <p>
     * - {@link Giudice}, viene restituito 1;
     * <p>
     * - {@link Partecipante}, viene restituito 2;
     * <p>
     * - se è semplicemente istanza di {@link Utente_registrato} viene restituito 3;
     * <p>
     * - altrimenti se non è stato identificato -1;
     * <p>
     * Infine all' utente corrente viene assegnato l'utente trovato
     *
     *
     * @param email dell'utente da cercare
     * @param password dell'utente da cercare
     * @return un valore che va da 0-3 dove
     * ognuno fa riferimento ad' una classe differente a cui appartiene l'utente;
     * -1 nel caso non sia stato identificato
     * @throws IllegalArgumentException nel caso in cui non viene trovato l'utente
     */
    public int identificaUtente(String email,String password)throws IllegalArgumentException{

        Utente_registrato utente=findAccount(email, password);
        isIscrizioneScaduta();
        if(utente instanceof Organizzatore organizzatore)     {
            setUtenteCorrente(organizzatore);
            return 0;
        }
        else if(utente instanceof Giudice giudice)
        {
            setUtenteCorrente(giudice);
            setHackathonCorrente(  findHackId(giudice.getIDHackatlon() )  );
            return 1;
        }

        else if (utente instanceof Partecipante partecipante)  {
            setUtenteCorrente(partecipante);
            setHackathonCorrente( findHackId(partecipante.getIDHackathon()) );
            setTeamCorrente( findIDTeam(partecipante.getIDTeam(), hackathonCorrente));
            return 2;
        }

        else if(utente instanceof Utente_registrato utenteReg) {
            setUtenteCorrente(utenteReg);
            if(!isOpenIscri())
                return 3;

            else
                return 4;
        }
        return -1;
    }


    /**
     * Funzione che viene utilizzata per estrarre le costanti dal DB, sulle quali verranno creati i codici ID personalizzati
     * dei vari utenti della piattaforma. Viene utilizzata la funzione{@link HackathonDAOImplement#getCostantiID()}
     * @throws SQLException
     */
    public void getCostantiIdDB() throws SQLException{

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement() ;

        int[] costanti= hackathonDAOImplement.getCostantiID();
        Hackathon.setnH(        costanti[0]);
        Team.setnT(             costanti[1]);
        Utente_registrato.setnU(costanti[2]);
        Partecipante.setnP(     costanti[3]);
        Giudice.setnG(          costanti[4]);



    }

    /**
     * Estrae le Flag inerenti al funzionamento coerente della piattaforma in base agli eventi accaduti.
     * Viene utilizzata la funzione{@link HackathonDAOImplement#getAllFlagsPiattaforma()}
     * @throws SQLException
     */
    public void getFlagsPiattaformaDB() throws SQLException{

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        boolean[] flagsPiattaforma= hackathonDAOImplement.getAllFlagsPiattaforma();

        piattaforma.setOpen_iscr(  flagsPiattaforma[0]);
        piattaforma.setEventoPronto(  flagsPiattaforma[1]);
    }

    /**
     * Estrae la data di inizio evento dal DB, tramite la funzione {@link HackathonDAOImplement#getDataEvento()}
     */
    public void getDataEventoDB(){

        try{
            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

            String dataEventoStr=hackathonDAOImplement.getDataEvento();
            if(dataEventoStr==null)
                return;
            LocalDate dataEvento= LocalDate.parse(dataEventoStr, DateTimeFormatter.ISO_LOCAL_DATE);
            piattaforma.setDataEvento( dataEvento );

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //OPERAZIONE UTENTE REGISTRATO


    //PRIMA DELLE ISCRIZIONI

    /**
     * Crea una nuova istanza di giudice avente le stesse credenziali dell'utente corrente, elimina l'utente corrente
     * dalla piattaforma e inserisce il giudice appena creato sia alla lista degli utenti della piattaforma
     * che alla lista giudici dell'Hackathon a cui è associato.
     * Inoltre viene modificato utente corrente nel corrispettivo giodice nel DB,
     * con la funzione{@link HackathonDAOImplement#setUtenteToGiudiceDB};
     * e viene aggiornata la costante ID del giudice nel DB {@link HackathonDAOImplement#storeNG}
     * @throws SQLException
     */
    public void diventaGiudice() throws SQLException{

        Giudice nuovoGiudice=new Giudice(utenteCorrente.getNome(), utenteCorrente.getCognome(),
                utenteCorrente.getEmail(), utenteCorrente.getPassword(), utenteCorrente.getHackIDRichiesta() );

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setUtenteToGiudiceDB(utenteCorrente.getID(), nuovoGiudice.getID(), utenteCorrente.getHackIDRichiesta());
        hackathonDAOImplement.storeNG(Giudice.getnG());

        piattaforma.getListaUtenReg().remove(utenteCorrente);
        piattaforma.getListaUtenReg().add(nuovoGiudice);
        findHackId( utenteCorrente.getHackIDRichiesta() ).addGiudice( nuovoGiudice );
        //NELLA GUI FAR RIACCEDERE L'UTENTE

    }

    /**
     * Resetta la richiesta associata all'utente corrente ad' una Stringa vuota, aggiorna il suo contenuto anche sul DB
     * {@link HackathonDAOImplement#eliminaRichiestaGiudice(String)}
     * @throws SQLException
     */
    public void rifiutaInvitoGiudice() throws SQLException{

        utenteCorrente.setHackIDRichiesta("");

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.eliminaRichiestaGiudice( utenteCorrente.getID() );
    }




    //DOPO LE ISCRIZIONI

    /**
     * Tale funzione crea un team inserendo l'Utente corrente come Partecipante{@link Utente_registrato#creaTeam(Piattaforma, Hackathon, String)}
     * Successivamente tale Team viene inserito nella lista Team dell'Hackathon corrente, incrementando anche il numero di partecipanti all'hackathon.
     * Crea un istanza di Partecipante con i stessi dati dell'utente corrente e sostituisce quest'ultimo nella
     * lista di utenti registrati alla piattaforma.
     * Tali aggiornamenti vengono poi salvati nel DB
     * {@link HackathonDAOImplement#addTeamsDB}
     * {@link HackathonDAOImplement#setUtenteToPartecipanteDB}
     * {@link HackathonDAOImplement#storeNP}
     * {@link HackathonDAOImplement#storeNT}
     * @param nomeTeam
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public void creaTeamHC(String nomeTeam)throws IllegalArgumentException,SQLException{

        hackathonCorrente.incrementaNpartec();//throws numero di partecipanti all'hackathon massimo
        Team team=utenteCorrente.creaTeam(piattaforma,hackathonCorrente,nomeTeam);//throws numero di membri massimo
        //IMPORTANTISSIMO L'ORDINE DI QUESTE 2 FUNZIONI

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.addTeamsDB(team.getID(),team.getNome(),team.getCodiceAccesso(),team.getIDHackathon());

        Partecipante partecipante=team.getArrayPartecipante().get(0);
        hackathonDAOImplement.setUtenteToPartecipanteDB(utenteCorrente.getID(), partecipante.getID(), partecipante.getIDHackathon(), partecipante.getIDTeam());
        hackathonDAOImplement.storeNP( Partecipante.getnP());
        hackathonDAOImplement.storeNT(Team.getnT());
    }

    /**
     * Inserisce un nuovo partecipante al Team corrente, controllanto preventivamente che il numero di partecipanti all'Hackathon
     * e il numero massimo di membri del team non superino i rispettivi massimi
     * @throws IllegalArgumentException
     * @throws SQLException
     */
    public void addPartecToTeamCorrHC() throws IllegalArgumentException,SQLException{

        Partecipante partecipante=null;

        if(hackathonCorrente.getNumeroPartec()<hackathonCorrente.N_MAX_PARTEC)
        {
            partecipante= utenteCorrente.registratiInTeam(piattaforma, hackathonCorrente,teamCorrente);//throws "numero di membri al team massimo"

        }
        hackathonCorrente.incrementaNpartec(); //throws "numero di partecipanti all'hackathon massimo", presente ulteriore controllo

        //IMPORTANTISSIMO L'ORDINE DI QUESTE 2 FUNZIONI

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        if(partecipante==null)
            return;

        hackathonDAOImplement.setUtenteToPartecipanteDB(utenteCorrente.getID(), partecipante.getID(),partecipante.getIDHackathon(), partecipante.getIDTeam());
        hackathonDAOImplement.storeNP( Partecipante.getnP());
        //inserimento nel DB del partecipante


    }


      //OPERAZIONE PARTECIPANTE

    /**
     * Carica il progresso nella lista progressi del team corrente e lo inserisce anche nel DB;
     * @param nomeProgresso Stringa, nome del progresso da inserire
     * @param codiceProgresso Stringa, contenuto del progresso (delle righe di codice)
     * @throws SQLException
     */
    public void caricaProgressoTeamCorr(String nomeProgresso,String codiceProgresso) throws SQLException
    {
        Progresso progresso=teamCorrente.caricaProgresso(nomeProgresso,codiceProgresso);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.addProgresso(progresso.getIdTeam(),progresso.getNome(),progresso.getCodiceProgresso());

    }

    /**
     * Inserisce/modifica il commento relativo ad un progresso del team corrente e lo salva nel DB
     * @param commento una stringa che rappresenta il nuovo commento da inserire
     * @param nomeProgresso il nome del progresso a cui è associato il commento
     * @throws SQLException
     */
    public void setCommentoTeamCorrente(String commento, String nomeProgresso) throws SQLException{

        Progresso progresso=findProgressoTeamCorrente(nomeProgresso);
        if(progresso!=null){
            progresso.setCommento(commento);

            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
            hackathonDAOImplement.setCommentoProgresso(teamCorrente.getID(),nomeProgresso,commento);
        }
    }

    /**
     * Cerca un progresso relativo al team corrente utilizzando il suo nome in input
     * @param nomeProgresso nome del progresso da ricercare
     * @return riferimento al progresso che si voleva cercare se esiste;
     * altrimenti ritorna NULL;
     */
    public Progresso findProgressoTeamCorrente(String nomeProgresso){

        for(Progresso progresso: teamCorrente.getArrayProgresso())
        {
            if(progresso.getNome().equals(nomeProgresso))
                return progresso;
        }
        return null;
    }



    //  OPERAZIONI AMMINISTRATORE

    /**
     * Aggiunge un nuovo hackathon alla piattaforma, con le caratteristiche inserite in input.
     * Viene successivamente aggiunto al DB con {@link HackathonDAOImplement#addHackathonDB(String, String, String, int)}
     * @param nomeHackathon nome dell'hackathon
     * @param sede dove si terrà l hackathon
     * @param durata durata dell'hackathon in giorni
     * @throws SQLException nel caso in cui l'aggiunta al DB non vada a buon fine
     */
    public void addHackathon(String nomeHackathon,String sede, int durata) throws SQLException
    {
        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        Hackathon hackathon;
        if(utenteCorrente instanceof Organizzatore organizzatore) {
            hackathon = organizzatore.creaHackathon(piattaforma, nomeHackathon,sede,durata);
            hackathonDAOImplement.storeNH(Hackathon.getnH());
            hackathonDAOImplement.addHackathonDB(hackathon.getID(),hackathon.getNome(),hackathon.getSede(),hackathon.getDurata());


        }

    }

    /**Imposta la flag "eventoFinito", appartenente all'hackathon corrente, allo stesso valore dell'input.
     * Poi la salva nel DB con{@link HackathonDAOImplement#setEventoFinito(boolean, String)}
     *
     * @param isEventoFinito valore booleano a cui sarà settata la flag "eventoFinito"
     * @throws SQLException
     */
    public void setEventoFinito(boolean isEventoFinito) throws SQLException{

        hackathonCorrente.setEventoFinito(isEventoFinito);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setEventoFinito(true, hackathonCorrente.getID());
    }

    /**
     * Rimuove l'hackathon corrente dalla piattaforma e dal DB, utilizzando la funzione {@link #removeHackathon(Hackathon)}
     * @throws SQLException nel caso in cui l'inserimento nel DB non vada a buon fine
     */
    public void removeHackCorr() throws SQLException{
        removeHackathon(hackathonCorrente);
    }

    /**
     * Rimuove un hackathon dalla piattaforma, il cui riferimento è il parametro della funzione.
     * Viene rimosso anche dal DB {@link HackathonDAOImplement#removeHackathonDB(String)}
     * @param hackathon riferimento dell'hackathon da eliminare
     * @throws SQLException
     */
    public void removeHackathon(Hackathon hackathon)throws SQLException{

        Giudice  giudice;
        if(!hackathon.getListaGiudici().isEmpty())
        {
            for(int i=0;i<hackathon.getListaGiudici().size();)
            {
                giudice=hackathon.getListaGiudici().get(i);
                System.out.println(giudice.getNome()+giudice.getCognome()+giudice.getID());
                removeGiudice(giudice,hackathon);
            }
        }

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        piattaforma.getListaHackathon().remove(hackathon);
        hackathonDAOImplement.removeHackathonDB(hackathon.getID());

    }

    /**
     * Imposta la data di inizio degli hackathon e la memorizza nel DB {@link HackathonDAOImplement#setDataEvento(String)}.
     * Viene utilizzata una funzione fi normalizzazione per prendere l'input e renderlo una data{@link #normalizzaData(int, int, int)}
     * @param giorno giorno in cui inizia
     * @param mese mese in cui inizia
     * @param anno anno in cui inizia
     * @throws IllegalArgumentException se la data inserita è futura o odierna
     */
    public void setDataEvento(int giorno,int mese,int anno) throws IllegalArgumentException{


        String dataInput=normalizzaData(giorno,mese,anno);
        LocalDate dataImpostata=LocalDate.parse(dataInput, DateTimeFormatter.ISO_LOCAL_DATE);

        if(dataImpostata.isBefore(LocalDate.now()) || dataImpostata.isEqual(LocalDate.now()))
            throw new IllegalArgumentException("Devi inserire una data futura");
        else{
            piattaforma.setDataEvento(dataInput);
            try{
                HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
                hackathonDAOImplement.setDataEvento(dataInput);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Serve per normalizzare una data prendendo come input degli interi che rappresentano: giorno, mese e anno.
     * Da come valore di ritorno una stringa che rappresenta la data in un modello AAAA-MM-GG;
     *
     * @param giorno intero che rappresenta il giorno
     * @param mese intero che rappresenta il mese
     * @param anno intero che rappresenta l'anno
     * @return una stringa nel modello AAAA-MM-GG
     */
    public String normalizzaData(int giorno,int mese,int anno){

        String giornoStr;
        String meseStr;
        String annoStr=String.valueOf(anno);

        if(giorno<10)
            giornoStr="0"+giorno;
        else
            giornoStr=String.valueOf(giorno);

        if(mese<10)
            meseStr="0"+mese;
        else
            meseStr=String.valueOf(mese);

        return  annoStr+"-"+meseStr+"-"+giornoStr;
    }

    //Selezione giudice senza richiesta
    public void addGiudiceHackCorr(String utenteID) throws IllegalArgumentException{

        Utente_registrato utente= trovaUtenteForGiudice(utenteID);
        if(utenteCorrente instanceof Organizzatore organizzatore)
            organizzatore.SelezionaGiudice( utente, piattaforma, hackathonCorrente);


    }

    /**
     * Salva una richiesta per essere giudice all'interno dell attributo 'richiesta' dell' utente selezionato.
     * Viene poi memorizzata anche nel DB con {@link HackathonDAOImplement#salvaRichiestaDB(String, String, String)}
     * @throws SQLException se la memorizzazione nel DB non va a buon fine
     */
    public void mandaRichiestaUtenteSelForGiudiceHackCorr() throws SQLException{


        String richiesta="Vuoi essere giudice dell'hackathon:\n"+hackathonCorrente.getNome();
        utenteSelezionato.setHackIDRichiesta(hackathonCorrente.getID());
        utenteSelezionato.setRichiestaGiudice(richiesta);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.salvaRichiestaDB(utenteSelezionato.getID(), hackathonCorrente.getID(), richiesta);

    }

    /**
     * Cerca un utente tramite il suo codice ID e restituisce un riferimento ad esso una volta ritrovato
     * @param utenteID codice id dell'utente che si vuo cercare
     * @return il riferimento all'utente che si voleva cercare
     * @throws IllegalArgumentException quest'eccezione viene lanciata in vari casi:
     * si inserisce l id dell'organizzatore, si seleziona un utente già giudice, si seleziona un utente avente già una richiesta
     * oppure se l utente non viene trovato
     */
    public Utente_registrato trovaUtenteForGiudice(String utenteID)throws IllegalArgumentException{

        if(utenteID.equals("0000"))
            throw new IllegalArgumentException("L'amministratore non può essere un giudice");

        for(Utente_registrato utente: piattaforma.getListaUtenReg())
        {
            if(utente.getID().equals(utenteID)){

                if(utente instanceof Giudice)
                {throw new IllegalArgumentException("L'utente scelto è già giudice");}

                else
                {
                    if(utente.getRichiestaGiudice().isEmpty()){

                        return utente;
                    }

                    else
                        throw new IllegalArgumentException("L'utente ha già una richiesta per essere giudice");
                }

            }
        }
        throw new IllegalArgumentException("ID Utente non trovato");
    }


    /**
     * Rimuove un giudice dalla piattaforma rendendolo nuovamente utente {@link Organizzatore#rimuoviGiudice(Giudice, Piattaforma, Hackathon)};
     * stessa modifica avviene nel DB {@link HackathonDAOImplement#setGiudiceToUtenteDB(String, String)}.
     * @param giudice riferimento al giudice da eliminare
     * @param hackathon hackathon a cui appartiene
     * @throws SQLException nel caso in cui la rimozione dal DB non avvenga correttamente
     */
    public void removeGiudice(Giudice giudice,Hackathon hackathon)throws SQLException{
        Utente_registrato utenteFromGiudice;
        if(utenteCorrente instanceof Organizzatore organizzatore){
            utenteFromGiudice=organizzatore.rimuoviGiudice(giudice,piattaforma,hackathon);

            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
            hackathonDAOImplement.setGiudiceToUtenteDB(utenteFromGiudice.getID(),giudice.getID());
            hackathonDAOImplement.storeNU(Utente_registrato.getnU());
        }
    }

    /**
     * Rimuove il giudice selezionato dall'hackathon corrente utilizzamdo la funzione{@link #removeGiudice(Giudice, Hackathon)}
     * @throws SQLException nel caso in cui la rimozione dal DB sollevi eccezioni
     */
    public void removeGiudiceSelHackCorr() throws SQLException{

        removeGiudice(giudiceSelezionato,hackathonCorrente);
    }

    /**
     * Apre le iscrizione per gli hackathon, impostando la flag associata a tale evento come TRUE tramite {@link Organizzatore#apriIscrizioni(Piattaforma)},
     * tale valore viene poi memorizzato nel DB {@link HackathonDAOImplement#setOpenIscr(boolean)}
     * @throws SQLException lancia un eccezione se vi sono problemi nella memorizzazione
     */
    public void apriIscrizioni() throws SQLException{
        if(utenteCorrente instanceof Organizzatore organizzatore){
            organizzatore.apriIscrizioni(this.piattaforma);
            removeHackathonNoGiudici();

            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
            hackathonDAOImplement.setOpenIscr(true);

        }

    }

    /**
     * Chiude le iscrizioni per gli hackathon, impostando la flag openIscr di piattaforma a FALSE e la flag eventoPronto a TRUE,
     * tramite la funzione {@link Organizzatore#chiudiIscrizioni(Piattaforma)};
     * Inoltre, per ogni hackathon, se i team sono sufficienti flag team_suff verrà impostata a TRUE altrimenti a FALSE
     * questo tramite la funzione {@link Hackathon#fareHackathonFromNTeams()}
     * I valori di tali flag vengono poi salvate nel DB con {@link HackathonDAOImplement#setOpenIscr(boolean)} e
     * {@link HackathonDAOImplement#setEventoPronto(boolean)}
     * @throws SQLException nel caso in cui si verifichino problemi nell' aggiornamento delle flag nel DB
     */
    public void chiudiIscrizioni()throws SQLException{

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        if(utenteCorrente instanceof Organizzatore organizzatore){

            organizzatore.chiudiIscrizioni(this.piattaforma);

            hackathonDAOImplement.setOpenIscr(false);
            hackathonDAOImplement.setEventoPronto(true);


        }


        for(Hackathon hackathon: piattaforma.getListaHackathon()){
            hackathon.fareHackathonFromNTeams();
            hackathonDAOImplement.setTeamSuff( hackathon.isTeam_suffic(), hackathon.getID() );
        }

    }

    /**
     * Cerca un giudice conoscendo l'Hacakthon a cui appartiene e il suo ID (parametri di ingresso)
     * @param hackathon hackathon cui appartiene il giudice
     * @param idGiudice id del giudice che si sta cercando
     * @return il riferimento al giudice trovato
     * @throws IllegalArgumentException se tale giudice non viene trovato
     */
    public Giudice findIdGiudice(Hackathon hackathon,String idGiudice)throws IllegalArgumentException{
        for(Giudice giudice: hackathon.getListaGiudici())
        {
            if(giudice.getID().equals(idGiudice)){
                return giudice;

            }
        }
        throw new IllegalArgumentException("ID Giudice non trovato");
    }

    /**
     * Utilizza la funzione {@link #findIdGiudice(Hackathon, String)} inserendo come primo argomento hackathonCorrente
     * e come secondo l id del giudice da cercare, il suo risultato viene poi assegnato a giudiceSelezionato.
     * @param idGiudice id del giudice da cercare
     */
    public void identificaGiudiceSelezionatoHackCorr(String idGiudice){

        giudiceSelezionato=findIdGiudice(hackathonCorrente,idGiudice);

    }

    /**
     *
     * @return il nome di {@link #giudiceSelezionato}
     */
    public String getNomeGiudSel(){return giudiceSelezionato.getNome();}

    /**
     *
     * @return il cognome di {@link #giudiceSelezionato}
     */
    public String getCognomeGiudSel(){return giudiceSelezionato.getCognome();}


    /**
     * Cerca un utente conoscendone l id che abbia le credenziali per essere giudice usando la funzione
     * {@link #trovaUtenteForGiudice(String)} e il valore di ritorno viene assegnato a {@link #utenteSelezionato}
     * @param idUtente id dell'utente da cercare
     * @throws IllegalArgumentException sollevato dalla funzione interna {@link #trovaUtenteForGiudice(String)} nel caso in cui
     * tale utente non viene trovato
     */
    public void identificaUtenteSelezionatoHackCorr(String idUtente)throws IllegalArgumentException{

        utenteSelezionato=trovaUtenteForGiudice(idUtente);

    }

    /**
     *
     * @return il nome dell'{@link #utenteSelezionato}
     */
    public String getNomeUtenSele(){ return utenteSelezionato.getNome();}

    /**
     *
     * @return il cognome dell'{@link #utenteSelezionato}
     */
    public String getCognomeUtenSele(){ return utenteSelezionato.getCognome();}


    /**
     * Rende pubblica la classifica ai vari utenti impostando la flag 'classificaPubblicata' di {@link #hackathonCorrente} a TRUE;
     * tale valore viene poi salvato nel DB con la funzione {@link HackathonDAOImplement#setClassPubblicata(boolean, String)}
     * @throws SQLException
     */
    public void pubblicaClassifica() throws SQLException{

        hackathonCorrente.setClassificaPubblicata(true);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();

        hackathonDAOImplement.setClassPubblicata(true, hackathonCorrente.getID());
    }



     // OPERAZIONI GIUDICE

    /**
     * Assegna un voto al {@link #teamCorrente} usando la funzione {@link Giudice#ValutaTeam(Team, String)};
     * Successivamente viene salvato nel DB con {@link HackathonDAOImplement#setVotoTeam(String, float)}
     * @param votoString rappresenta il voto assegnato dal giudice
     * @throws IllegalArgumentException eccezione lanciata da {@link Giudice#ValutaTeam(Team, String)}
     * nel caso in cui il formato del voto in input non è compatibile con un voto decimale
     * @throws SQLException se la memorizzazione nel DB non va a buon fine
     */
    public void assegnaVotoTeamCorr(String votoString)throws IllegalArgumentException,SQLException{

        if(utenteCorrente instanceof Giudice giudice)
        {
            giudice.ValutaTeam(teamCorrente,votoString);

            HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
            hackathonDAOImplement.setVotoTeam(teamCorrente.getID(), Float.parseFloat(votoString));
        }
    }

    /**
     * Prende una stringa in input e la salva come traccia {@link #hackathonCorrente} nell'attributo 'problema'
     * successivamente viene salvata nel DB tramite la funzione{@link HackathonDAOImplement#setHackathonProblema(String, String)}
     * @param problema stringa che rappresenta la traccia del problema
     * @throws SQLException eccezione lanciata nel caso si verifichino problemi per la memorizzazione nel DB
     */
    public void salvaProblema(String problema) throws SQLException{
        hackathonCorrente.setProblema(problema);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setHackathonProblema(hackathonCorrente.getID(), problema);
        //DB Set problema

    }

    /**
     * Imposta la flag 'view_problema' di {@link #hackathonCorrente} a TRUE.
     * Viene poi salvato il suo valore all'interno del DB tramite {@link HackathonDAOImplement#setViewProblema(boolean, String)}
     * @throws SQLException nel caso in cui la memorizzazione di tale valore nel DB sollevi eccezioni
     */
    public void pubblicaProblema()throws SQLException{

        hackathonCorrente.setView_problema(true);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setViewProblema(true, hackathonCorrente.getID());

        //DB Flag view problema
    }

    /**
     * Imposta la flag 'votazioneConclusa' dell {@link #hackathonCorrente} a TRUE e salva tale valore nel DB tramite
     * {@link HackathonDAOImplement#setVotazConclusa(boolean, String)}
     * @throws SQLException nel caso in cui la memorizzazione di tale valore nel DB sollevi eccezioni
     */
    public void concludiVotazioni()throws SQLException{

        hackathonCorrente.setVotazioneConclusa(true);

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.setVotazConclusa(true, hackathonCorrente.getID());
    }





 // OPERAZIONI SUI TEAM

    /**
     * Assegna a {@link #teamCorrente} il valore del riferimento in input
     * @param teamCorrente riferimento a {@link Team} che viene assegnato a {@link #teamCorrente}
     */
    public void setTeamCorrente(Team teamCorrente) {    this.teamCorrente = teamCorrente;}

    /**
     *
     * @param codiceAccesso
     * @throws IllegalArgumentException
     */
    public void identificaCODTeamHC(String codiceAccesso) throws IllegalArgumentException{
        teamCorrente=findCodeAccessTeam(codiceAccesso,hackathonCorrente);
    }
    public void identificaTeamHackCorr(String idTeam) throws IllegalArgumentException{

        Team team=findIDTeam(idTeam,hackathonCorrente);
        teamCorrente=team;

    }

    public String getNomeTeamCorr(){return teamCorrente.getNome();}
    public String getIdTeamCorr(){return teamCorrente.getID();}
    public String getCodiceAccessoTeamCorr(){ return teamCorrente.getCodiceAccesso();}
    public float getVotoTeamCorr(){return teamCorrente.getVoto();}



    public void getAllTeamDB()throws SQLException{
        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> nome = new ArrayList<>();
        ArrayList<String> codAccesso = new ArrayList<>();

        ArrayList<Float> voti = new ArrayList<>();
        ArrayList<String> idHackathon = new ArrayList<>();

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.getAllTeamsDB(id,nome, codAccesso,voti,idHackathon);

        for(int i=0; i<id.size();i++){
            Team team=new Team(id.get(i),  nome.get(i), codAccesso.get(i), voti.get(i), idHackathon.get(i));
            findHackId( team.getIDHackathon() ).addTeam(team);
            getProgressiTeamDB(team);

        }

    }


    private void getProgressiTeamDB(Team team) throws SQLException{

        ArrayList<String> nome = new ArrayList<>();
        ArrayList<String> codice=new ArrayList<>();
        ArrayList<String> commento=new ArrayList<>();

        HackathonDAOImplement hackathonDao=new HackathonDAOImplement();
        hackathonDao.getProgressiTeam(nome,codice,commento, team.getID());

        for(int i=0;i< nome.size();i++){

            Progresso progresso=new Progresso();
            progresso.setNome( nome.get(i));
            progresso.setCodiceProgresso(codice.get(i));
            progresso.setCommento(commento.get(i));
            progresso.setIdTeam(  team.getID());

            team.getArrayProgresso().add(0, progresso);
        }


    }



    public Team findIDTeam(String id, Hackathon hackathon) throws IllegalArgumentException
    {
        for(Team team: hackathon.getListaTeam())
        {
            if(team.getID().equals(id))
                return team;
        }
        throw new IllegalArgumentException("ID Team non trovato");
    }

    public Team findCodeAccessTeam(String codeAccess,Hackathon hackathon) throws IllegalArgumentException
    {
        for(Team team: hackathon.getListaTeam())
        {
            if(team.getCodiceAccesso().equals(codeAccess)) return team;
        }

        throw new IllegalArgumentException("Codice di accesso errato");
    }

    public boolean areAllTeamValuated(){

        for(Team team: hackathonCorrente.getListaTeam())
        {
            if(team.getVoto()==-1)
            {
                return false;
            }

        }
        return true;
    }

    public void identificaProgressoTC(String nomeProgresso){

        for(Progresso progresso: teamCorrente.getArrayProgresso()){
            if(progresso.getNome().equals(nomeProgresso))
                progressoCorrente=progresso;
        }
    }

    public String getCodiceProgCorr(){return progressoCorrente.getCodiceProgresso();}
    public String getNomeProgCorr(){return progressoCorrente.getNome();}
    public String getCommentoProgCorr(){return progressoCorrente.getCommento();}

    public ArrayList<String> getNomiProgressTeamCorr(){

        ArrayList<String> nomeProgressi=new ArrayList<>();
        for(Progresso progresso: teamCorrente.getArrayProgresso())
            nomeProgressi.add(progresso.getNome());
        return nomeProgressi;
    }



    public void ordinaTeamVoti(){ //Dal voto migliore (più alto) al peggiore (più basso) dunque decrescente

        Team maxTeam;
        Team corrTeam;
        Team ausTeam;
        int max;
        for(int i=0;i<hackathonCorrente.getListaTeam().size();i++)
        {
            max =i;
            maxTeam =hackathonCorrente.getListaTeam().get(i);

            for(int j=i;j<hackathonCorrente.getListaTeam().size();j++)
            {
                corrTeam=hackathonCorrente.getListaTeam().get(j);

                if(corrTeam.getVoto()> maxTeam.getVoto()){
                    maxTeam =corrTeam;
                    max =j;
                }
            }
            ausTeam=hackathonCorrente.getListaTeam().get(i);
            hackathonCorrente.getListaTeam().set(max,ausTeam);
            hackathonCorrente.getListaTeam().set(i, maxTeam);


        }

    }



    public int calcolaPosizionamentoTeamCorrente(){

        int posiz=0;

        for(Team team: hackathonCorrente.getListaTeam()){
            if (team.getID().equals(teamCorrente.getID()))
            {
                posiz=hackathonCorrente.getListaTeam().indexOf(team)+1;
                break;
            }
        }
        return posiz;
    }


    public boolean isNomeProgressoOriginale(String nomeProgresso){

        for(Progresso progresso: teamCorrente.getArrayProgresso()){

            if(progresso.getNome().equals(nomeProgresso))
                return false;
        }
        return true;
    }




    /**
     * OPERAZIONI HACKATHON
     */


    public void getAllHackathonDB()throws SQLException{

        ArrayList<String> id=new ArrayList<>();
        ArrayList<String> nome=new ArrayList<>();
        ArrayList<Integer> nMaxPartec =new ArrayList<>();
        ArrayList<Integer> nTeam =new ArrayList<>();
        ArrayList<String> problema=new ArrayList<>();
        ArrayList<Boolean> teamSuff =new ArrayList<>();
        ArrayList<Boolean> viewProblema =new ArrayList<>();
        ArrayList<Boolean> eventoFinito =new ArrayList<>();
        ArrayList<Boolean> votazConclusa =new ArrayList<>();
        ArrayList<Boolean> classPubblicata =new ArrayList<>();
        ArrayList<String> sede=new ArrayList<>();
        ArrayList<Integer> durata=new ArrayList<>();

        HackathonDAOImplement hackathonDAOImplement=new HackathonDAOImplement();
        hackathonDAOImplement.getAllHackathonDB(id,nome, nMaxPartec, nTeam,problema, teamSuff, viewProblema, eventoFinito, votazConclusa, classPubblicata,sede,durata);

        for(int i=0;i<id.size();i++){
            Hackathon hackathon=new Hackathon(id.get(i),nome.get(i), nTeam.get(i), problema.get(i), teamSuff.get(i),
                    viewProblema.get(i), eventoFinito.get(i), votazConclusa.get(i), classPubblicata.get(i),
                    sede.get(i),durata.get(i));

            piattaforma.getListaHackathon().add(hackathon);
        }




    }

    public void identificaHackathon(String idHackathon){
         hackathonCorrente=findHackId(idHackathon);

    }
    public void setHackathonCorrente(Hackathon hackathonCorrente) {this.hackathonCorrente = hackathonCorrente;}


    public String getIdHackCorr(){return hackathonCorrente.getID();}
    public String getNomeHackCorr(){return hackathonCorrente.getNome();}
    public String getProblemaHackCorr(){return hackathonCorrente.getProblema();}



    public boolean isListaGiudHackCorrEmpty(){

        return hackathonCorrente.getListaGiudici().isEmpty();
    }

    public boolean isTeamSuffHackCorr(){return hackathonCorrente.isTeam_suffic();}
    public boolean isViewProblemaHackCorr(){ return hackathonCorrente.isView_problema();}
    public boolean isEventoFinitoHackCorr(){return hackathonCorrente.isEventoFinito();}
    public boolean isVotazioneConclusaHackCorr(){return hackathonCorrente.isVotazioneConclusa();}
    public boolean isClassificaPubblHackCorr(){return hackathonCorrente.isClassificaPubblicata();}

    public Hackathon findHackId(String id) throws IllegalArgumentException
    {
        for(Hackathon hackathon: piattaforma.getListaHackathon())
        {
            if(hackathon.getID().equals(id))
                return hackathon;
        }
        throw new IllegalArgumentException("ID non presente");
    }


    public void removeHackathonNoGiudici() throws  SQLException{

        Hackathon hackathon;
        for(int i=0;i<getListaHackathon().size();)
        {
            hackathon=getListaHackathon().get(i);

            if(hackathon.getListaGiudici().isEmpty())
                removeHackathon(hackathon);
            else
                i++;


        }
    }


//PER LE TABELLE

    public String  nomeHackTab(int row){
        return piattaforma.getListaHackathon().get(row).getNome();
    }

    public String idHackTab(int row){
        return piattaforma.getListaHackathon().get(row).getID();
    }

    public String sedeHackTab(int row){
        return piattaforma.getListaHackathon().get(row).getSede();
    }

    public int durataHackTab(int row){
        return piattaforma.getListaHackathon().get(row).getDurata();
    }

    public int numPartecHackTab(int row){
        return piattaforma.getListaHackathon().get(row).getNumeroPartec();
    }

    public int getSizeListaHack(){
        return piattaforma.getListaHackathon().size();
    }


    //TAB GIUDICE
    public String  nomeGiudTab(int row){
        return hackathonCorrente.getListaGiudici().get(row).getNome();
    }

    public String  idGiudTab(int row){
        return hackathonCorrente.getListaGiudici().get(row).getID();
    }
    public String  cognGiudTab(int row){
        return hackathonCorrente.getListaGiudici().get(row).getCognome();
    }
    public String  emailGiudTab(int row){
        return hackathonCorrente.getListaGiudici().get(row).getEmail();
    }
    public int getSizeListaGiud(){
        return hackathonCorrente.getListaGiudici().size();
    }

    //TAB PARTECIPANTI
    public String idPartecTab(int row){
        return teamCorrente.getArrayPartecipante().get(row).getID();
    }

    public String nomePartecTab(int row){
        return teamCorrente.getArrayPartecipante().get(row).getNome();
    }

    public String cognPartecTab(int row){
        return teamCorrente.getArrayPartecipante().get(row).getCognome();
    }

    public String emailPartecTab(int row){
        return teamCorrente.getArrayPartecipante().get(row).getEmail();
    }

    public int sizeListaPartec()
    {
        return teamCorrente.getArrayPartecipante().size();
    }

    public String idTeamTab(int row){
        return hackathonCorrente.getListaTeam().get(row).getID();
    }

    public String nomeTeamTab(int row){
        return hackathonCorrente.getListaTeam().get(row).getNome();
    }

    public int numMembriTeamTab(int row){
        return hackathonCorrente.getListaTeam().get(row).getNumeroMembri();
    }

    public float votoTeamTab(int row){
        return hackathonCorrente.getListaTeam().get(row).getVoto();
    }

    public int sizeListaTeam(){
        return hackathonCorrente.getListaTeam().size();
    }





    public String idUtenTab(int row){
        return piattaforma.getListaUtenReg().get(row).getID();
    }

    public String nomeUtenTab(int row){
        return piattaforma.getListaUtenReg().get(row).getNome();
    }

    public String cognUtenTab(int row){
        return piattaforma.getListaUtenReg().get(row).getCognome();
    }

    public String emailUtenTab(int row){
        return piattaforma.getListaUtenReg().get(row).getEmail();
    }

    public int sizeListaUtenReg(){
        return piattaforma.getListaUtenReg().size();
    }

}
