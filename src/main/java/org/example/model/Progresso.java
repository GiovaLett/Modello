package org.example.model;

public class Progresso {

    private String nome="";
    private String codiceProgresso=""; //Sarebbe il progresso effettivo
    private String commento="";
    private String idTeam;

    public Progresso(){}

    public Progresso(String nome,String codiceProgresso,String commento,String idTeam){

        this.nome=nome;   this.codiceProgresso=codiceProgresso;   this.commento=commento;
        this.idTeam=idTeam;
    }

    public void setNome(String nome) {this.nome = nome;}

    public String getNome() {return nome;}

    public String getCodiceProgresso() {return codiceProgresso;}

    public void setCodiceProgresso(String codiceProgresso) {this.codiceProgresso = codiceProgresso;}

    public String getCommento() {return commento;}

    public void setCommento(String commento) {this.commento = commento;}

    public String getIdTeam() {return idTeam;}

    public void setIdTeam(String idTeam) {this.idTeam = idTeam;}
}
