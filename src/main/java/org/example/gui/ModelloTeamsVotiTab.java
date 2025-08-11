package org.example.gui;


import org.example.controller.Controller;

import javax.swing.table.AbstractTableModel;


public class ModelloTeamsVotiTab extends AbstractTableModel {

    Controller contr;


    private final String[] nomeColonne={"ID","Nome","Numero membri","Voto"};


    ModelloTeamsVotiTab(Controller c){
        contr=c;
    }

    @Override
    public String getColumnName(int column) {
        return nomeColonne[column];
    }

    @Override
    public int getRowCount() {
        return contr.sizeListaTeam();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {



        if(columnIndex==0){ return contr.idTeamTab(rowIndex);}
        if(columnIndex==1){return contr.nomeTeamTab(rowIndex);}
        if(columnIndex==2){return contr.numMembriTeamTab(rowIndex);}
        if(columnIndex==3){return contr.votoTeamTab(rowIndex);}

        return null;
    }
}
