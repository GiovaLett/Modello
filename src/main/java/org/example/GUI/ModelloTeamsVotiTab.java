package org.example.GUI;

import org.example.Model.ruoli.Team;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelloTeamsVotiTab extends AbstractTableModel {

    ArrayList<Team> ListaTeam;

    private String[] nomeColonne={"ID","Nome","Numero membri","Voto"};

    ModelloTeamsVotiTab(ArrayList<Team> listaTeam){ListaTeam=listaTeam;}

    @Override
    public String getColumnName(int column) {
        return nomeColonne[column];
    }

    @Override
    public int getRowCount() {
        return ListaTeam.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Team teamRow=ListaTeam.get(rowIndex);

        if(columnIndex==0){ return teamRow.getID();}
        if(columnIndex==1){return teamRow.getNome();}
        if(columnIndex==2){return teamRow.getNumeroMembri();}
        if(columnIndex==3){return teamRow.getVoto();}

        return null;
    }
}
