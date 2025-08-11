package org.example.model.ruoli;
import org.example.model.Hackathon;
import org.example.model.Piattaforma;

public class Organizzatore extends UtenteRegistrato
{



    public Organizzatore(String id, String nome, String cognome, String email, String password)
    {
        this.id = id;  this.nome=nome;  this.cognome=cognome;
        this.email=email; this.password=password;
    }

    public void apriIscrizioni(Piattaforma piattaforma) {piattaforma.setOpenIscr(true);}
    public void chiudiIscrizioni(Piattaforma piattaforma) {
        piattaforma.setOpenIscr(false);
        piattaforma.setEventoPronto(true);

    }


    public void SelezionaGiudice(UtenteRegistrato utente, Piattaforma piattaforma, Hackathon hackathon)
    {
        Giudice nuovoGiudice = new Giudice(utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getPassword(), hackathon.getId());
        hackathon.addGiudice(nuovoGiudice);
        piattaforma.getListaUtenReg().remove(utente);
        piattaforma.getListaUtenReg().add(nuovoGiudice);

    }

    public UtenteRegistrato rimuoviGiudice(Giudice giudice, Piattaforma piattaforma, Hackathon hackathon)
    {
        UtenteRegistrato utente=new UtenteRegistrato(giudice.getNome(),giudice.getCognome(),giudice.getEmail(), giudice.getPassword());
        piattaforma.getListaUtenReg().add(utente);
        piattaforma.getListaUtenReg().remove(giudice);
        hackathon.getListaGiudici().remove(giudice);
        return utente;
    }

    public Hackathon creaHackathon(Piattaforma piattaforma, String nomeHackathon,String sede, int durata){
        Hackathon nuovoHackathon=new Hackathon(nomeHackathon);
        nuovoHackathon.setSede(sede);
        nuovoHackathon.setDurata(durata);
        piattaforma.addHackathon(nuovoHackathon);
        return nuovoHackathon;
    }
}
