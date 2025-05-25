package org.example.GUI;

import org.example.Model.ruoli.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelloUtentiTabella extends AbstractTableModel {

    private ArrayList<Utente_registrato> ListaUtenti;
    private String[] nomeColonne ={"ID","Nome","Cognome","Email"};


    ModelloUtentiTabella(ArrayList<Utente_registrato> ListaUtenti){
        this.ListaUtenti=ListaUtenti;
    }




    @Override
    public int getRowCount() {
        return ListaUtenti.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        return nomeColonne[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) //ID,nome,cognome,email;
    {
        Utente_registrato utenteRow=ListaUtenti.get(rowIndex);

        if(columnIndex==0)  { return utenteRow.getID();}
        if(columnIndex==1)  {return utenteRow.getNome();}
        if(columnIndex==2)  {return utenteRow.getCognome();}
        if(columnIndex==3)  {return utenteRow.getEmail();}
        else  return null;




    }
}
