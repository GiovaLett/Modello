package org.example.GUI;


import org.example.controller.Controller;

import javax.swing.table.AbstractTableModel;


public class ModelloHackTab extends AbstractTableModel {

    Controller controller;

    String[] nomeColonne={"ID","Nome","Sede","Durata","Numero partecipanti"};




    ModelloHackTab(Controller c){
        controller=c;
    }

    @Override
    public String getColumnName(int column) {
        return nomeColonne[column];
    }


    public int getRowCount() {
        return controller.getSizeListaHack();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {

        if(columnIndex==0) {return controller.idHackTab(rowIndex);}
        if(columnIndex==1) {return controller.nomeHackTab(rowIndex);}
        if(columnIndex==2) {return controller.sedeHackTab(rowIndex);}
        if(columnIndex==3) {return controller.durataHackTab(rowIndex)+"g";}
        if(columnIndex==4) {return controller.numPartecHackTab(rowIndex);}
        return null;
    }
}
