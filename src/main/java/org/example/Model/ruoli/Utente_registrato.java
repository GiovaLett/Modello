package org.example.Model.ruoli;

public class Utente_registrato extends Utente {
    protected String email;
     protected String password;
    protected String ID;

    public Utente_registrato(String nome, String cognome, String email, String password, String ID) {
        super(nome, cognome);
        this.email = email;
        this.password = password;
        this.ID = ID;
    }
    public Utente_registrato() {}

    public String getEmail() {return email;}
    public String getPassword(){return password;}

    public String getID() {return ID;}
}