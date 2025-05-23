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

    public void apriIscrizioni(Piattaforma piattaforma){
        piattaforma.open_iscr =true;
    }


    public void SelezionaGiudice(String ID, Piattaforma piattaforma)
    {
        for(Utente_registrato uteReg: piattaforma.getListaUtenReg())
        {
            if(uteReg.ID.equals(ID))
            {
                System.out.println("Sei stato selezionato come giudice!: \n ACCETTA:1   DECLINA:0");//Simbolico implementarla meglio poi con GUI ecc..

            }
        }

    }
}
