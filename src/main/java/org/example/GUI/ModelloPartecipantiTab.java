package org.example.GUI;

import org.example.Model.ruoli.Giudice;
import org.example.Model.ruoli.Partecipante;
import org.example.Model.ruoli.Utente_registrato;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelloPartecipantiTab extends AbstractTableModel {
    private ArrayList<Partecipante> ListaPartecipanti;
    private String[] nomeColonne ={"ID","Nome","Cognome","Email"};


    ModelloPartecipantiTab(ArrayList<Partecipante> ListaPartecipanti){

        this.ListaPartecipanti = ListaPartecipanti;

    }


    @Override
    public int getRowCount() {
        return ListaPartecipanti.size();
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
        Utente_registrato utenteRow= ListaPartecipanti.get(rowIndex);

        if(columnIndex==0)  { return utenteRow.getID();}
        if(columnIndex==1)  {return utenteRow.getNome();}
        if(columnIndex==2)  {return utenteRow.getCognome();}
        if(columnIndex==3)  {return utenteRow.getEmail();}
        else  return null;




    }
}
