package org.example.ruoli;
import org.example.*;
public class Organizzatore extends Utente_registrato
{
    public void apriIscrizioni(Piattaforma piattaforma){
        piattaforma.vedi_problema=true;
    }


    public void SelezionaGiudice(String ID,Piattaforma piattaforma)
    {
        for(Utente_registrato uteReg: piattaforma.ArrayUtenReg )
        {
            if(uteReg.ID.equals(ID))
            {
                System.out.println("Sei stato selezionato come giudice!: \n ACCETTA:1   DECLINA:0");

            }
        }

    }
}
