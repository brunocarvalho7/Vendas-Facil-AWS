package br.ufc.mobile.vendasfacil.ui;

import java.util.List;

public class View {

    public interface ViewMaster<T>{
        void updateAdapter(List<T> dados);
    }

    public interface ViewDetails<T>{
        T getData();
        void finishActivity();
        void showText(String s);
    }

}
