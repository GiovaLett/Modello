package org.example.Model.ruoli;
import org.example.Model.Hackathon;
import org.example.Model.Piattaforma;

public class Organizzatore extends Utente_registrato
{


    public Organizzatore(){}
    public Organizzatore(String ID,String nome,String cognome,String email,String password)
    {
        this.ID=ID;  this.nome=nome;  this.cognome=cognome;
        this.email=email; this.password=password;
    }

    public void apriIscrizioni(Piattaforma piattaforma) {piattaforma.setOpen_iscr(true);}
    public void chiudiIscrizioni(Piattaforma piattaforma) {piattaforma.setOpen_iscr(false);}


    public void SelezionaGiudice(Utente_registrato utente, Piattaforma piattaforma,Hackathon hackathon)
    {
        Giudice nuovoGiudice = new Giudice(utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getPassword(), hackathon.getID());
        hackathon.addGiudice(nuovoGiudice);
        piattaforma.getListaUtenReg().remove(utente);
        piattaforma.getListaUtenReg().add(nuovoGiudice);

    }

    public void rimuoviGiudice(Giudice giudice, Piattaforma piattaforma,Hackathon hackathon)
    {
        Utente_registrato utente=new Utente_registrato(giudice.getNome(),giudice.getCognome(),giudice.getEmail(), giudice.getPassword());
        piattaforma.getListaUtenReg().add(utente);
        piattaforma.getListaUtenReg().remove(giudice);
        hackathon.getListaGiudici().remove(giudice);
    }

    public void creaHackathon(Piattaforma piattaforma, String nomeHackathon){
        piattaforma.addHackathon(new Hackathon(nomeHackathon));
    }
}
