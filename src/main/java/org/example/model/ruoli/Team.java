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



    public Team(String id, String nome, String codiceAccesso, float voto, String idHackathon){

        this.id =id;   this.nome=nome;   this.codiceAccesso = codiceAccesso;
        this.voto=voto;   this.idHackathon =idHackathon;
    }
    
    public Team(String nome, Hackathon hackathon){
        this.codiceAccesso =creaCodiceAccesso();   this.id = creaIDTeam();  this.idHackathon =hackathon.getId();  this.nome =nome;
    }

    public String getId() {return id;}



    public String getNome() {return nome;}

    public int getNumeroMembri() {return numeroMembri;}

    public String getCodiceAccesso(){
        return this.codiceAccesso;
    }

    public String getIdHackathon() {return idHackathon;}

    public float getVoto() {return voto;}

    public void setVoto(float voto) {this.voto = voto;}

    public ArrayList<Partecipante> getArrayPartecipante() {return arrayPartecipante;}

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
     * CARICAMENTO PROGRESSI
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

    public ArrayList<Progresso> getArrayProgresso() {return arrayProgresso;}

    private String creaIDTeam(){

        String idCodice ="-1";
        if(nT >=0 && nT <10)  idCodice ="T00"+nT;

        else if (nT <100)   idCodice ="T0"+nT;

        else if (nT <1000)   idCodice ="T"+nT;

        nT++;

        return idCodice;

    }
    public static int getnT() {
        return nT;
    }

    public static void setnT(int nT) {
        Team.nT = nT;
    }

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
