package org.example.GUI;


import org.example.controller.Controller;

import javax.swing.table.AbstractTableModel;


public class ModelloTeamsTab extends AbstractTableModel {

    Controller contr;


    private String[] nomeColonne={"ID","Nome","Numero membri"};



    ModelloTeamsTab(Controller c){
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
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {



        if(columnIndex==0){ return contr.idTeamTab(rowIndex);}
        if(columnIndex==1){return contr.nomeTeamTab(rowIndex);}
        if(columnIndex==2){return contr.numMembriTeamTab(rowIndex);}

        return null;
    }
}
