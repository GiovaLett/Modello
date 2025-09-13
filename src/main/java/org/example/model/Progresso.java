package org.example.model;

public class Progresso {

    private String nome="";
    private String codiceProgresso=""; //Sarebbe il progresso effettivo
    private String commento="";
    private String idTeam;

    /**
     * Costruttore vuoto della classe {@link Progresso}.
     * Inizializza un oggetto Progresso senza impostare alcun attributo.
     */
    public Progresso(){}

    /**
     * Costruttore della classe {@link Progresso} che inizializza tutti gli attributi.
     *
     * @param nome il nome del progresso
     * @param codiceProgresso il codice univoco del progresso
     * @param commento eventuale commento associato al progresso
     * @param idTeam l'id del team a cui il progresso appartiene
     */
    public Progresso(String nome,String codiceProgresso,String commento,String idTeam){

        this.nome=nome;   this.codiceProgresso=codiceProgresso;   this.commento=commento;
        this.idTeam=idTeam;
    }

    /**
     * Imposta il nome del progresso.
     *
     * @param nome il nome da assegnare al progresso
     */
    public void setNome(String nome) {this.nome = nome;}

    /**
     * Restituisce il nome del progresso.
     *
     * @return una {@link String} contenente il nome del progresso
     */
    public String getNome() {return nome;}

    /**
     * Restituisce il codice univoco del progresso.
     *
     * @return una {@link String} contenente il codice del progresso
     */
    public String getCodiceProgresso() {return codiceProgresso;}

    /**
     * Imposta il codice univoco del progresso.
     *
     * @param codiceProgresso il codice da assegnare al progresso
     */
    public void setCodiceProgresso(String codiceProgresso) {this.codiceProgresso = codiceProgresso;}

    /**
     * Restituisce il commento associato al progresso.
     *
     * @return una {@link String} contenente il commento del progresso
     */
    public String getCommento() {return commento;}

    /**
     * Imposta il commento associato al progresso.
     *
     * @param commento il commento da assegnare al progresso
     */
    public void setCommento(String commento) {this.commento = commento;}

    /**
     * Restituisce l'ID del team a cui appartiene il progresso.
     *
     * @return una {@link String} contenente l'ID del team
     */
    public String getIdTeam() {return idTeam;}

    /**
     * Imposta l'ID del team a cui appartiene il progresso.
     *
     * @param idTeam l'ID del team da assegnare al progresso
     */
    public void setIdTeam(String idTeam) {this.idTeam = idTeam;}
}
