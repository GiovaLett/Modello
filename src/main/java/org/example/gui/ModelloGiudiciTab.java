package org.example.gui;

import org.example.controller.Controller;

import javax.swing.table.AbstractTableModel;

public class ModelloGiudiciTab extends AbstractTableModel {

    Controller contr;

    private final String[] nomeColonne ={"ID","Nome","Cognome","Email"};



    ModelloGiudiciTab(Controller c){

        contr=c;

    }


    @Override
    public int getRowCount() {
        return contr.getSizeListaGiud();
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
    public Object getValueAt(int rowIndex, int columnIndex)
    {


        if(columnIndex==0)  { return contr.idGiudTab(rowIndex);}
        if(columnIndex==1)  {return contr.nomeGiudTab(rowIndex);}
        if(columnIndex==2)  {return contr.cognGiudTab(rowIndex);}
        if(columnIndex==3)  {return contr.emailGiudTab(rowIndex);}
        else  return null;




    }
}
