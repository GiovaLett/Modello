package org.example.GUI;

import org.example.Model.Hackathon;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelloHackTab extends AbstractTableModel {

    ArrayList<Hackathon> ListaHackathon;
    String[] nomeColonne={"ID","Nome","Numero partecipanti"};


    ModelloHackTab(ArrayList<Hackathon> listaHackathon) {
        ListaHackathon = listaHackathon;
    }

    @Override
    public String getColumnName(int column) {
        return nomeColonne[column];
    }


    public int getRowCount() {
        return ListaHackathon.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Hackathon hackathonRow=ListaHackathon.get(rowIndex);
        if(columnIndex==0) {return hackathonRow.getID();}
        if(columnIndex==1) {return hackathonRow.getNome();}
        if(columnIndex==2) {return hackathonRow.getNumeroPartec();}
        return null;
    }
}
