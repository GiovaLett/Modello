package org.example.gui;


import org.example.controller.Controller;

import javax.swing.table.AbstractTableModel;


public class ModelloPartecipantiTab extends AbstractTableModel {

    Controller contr;

    private final String[] nomeColonne ={"ID","Nome","Cognome","Email"};



    ModelloPartecipantiTab(Controller c){

        contr=c;

    }


    @Override
    public int getRowCount() {
        return contr.sizeListaPartec();
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


        if(columnIndex==0)  { return contr.idPartecTab(rowIndex);}
        if(columnIndex==1)  {return contr.nomePartecTab(rowIndex);}
        if(columnIndex==2)  {return contr.cognPartecTab(rowIndex);}
        if(columnIndex==3)  {return contr.emailPartecTab(rowIndex);}
        else  return null;




    }
}
