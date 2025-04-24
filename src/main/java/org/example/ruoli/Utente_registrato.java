package org.example.ruoli;

public class Utente_registrato extends Utente {
    public String email;
    String password;
    public String ID;

    public Utente_registrato(String nome, String cognome, String email, String password, String ID) {
        super(nome, cognome);
        this.email = email;
        this.password = password;
        this.ID = ID;
    }
    public Utente_registrato() {}
}