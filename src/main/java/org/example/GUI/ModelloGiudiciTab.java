package org.example.GUI;

import org.example.Model.ruoli.Giudice;
import org.example.Model.ruoli.Utente_registrato;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelloGiudiciTab extends AbstractTableModel {

    private ArrayList<Giudice> ListaGiudici;
    private String[] nomeColonne ={"ID","Nome","Cognome","Email"};


    ModelloGiudiciTab(ArrayList<Giudice> ListaGiudici){this.ListaGiudici = ListaGiudici;}


    @Override
    public int getRowCount() {
        return ListaGiudici.size();
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
        Utente_registrato utenteRow= ListaGiudici.get(rowIndex);

        if(columnIndex==0)  { return utenteRow.getID();}
        if(columnIndex==1)  {return utenteRow.getNome();}
        if(columnIndex==2)  {return utenteRow.getCognome();}
        if(columnIndex==3)  {return utenteRow.getEmail();}
        else  return null;




    }
}
