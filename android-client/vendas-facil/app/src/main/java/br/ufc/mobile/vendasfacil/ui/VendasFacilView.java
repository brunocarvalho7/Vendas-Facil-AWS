package br.ufc.mobile.vendasfacil.ui;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.UsuarioDTO;

public class VendasFacilView {

    public interface ViewMaster<T>{
        void updateAdapter(List<T> dados);
    }

    public interface ViewDetails<T>{
        T getData();
        void finishActivity();
        void showText(String s);
    }

    public interface ViewLogin{
        UsuarioDTO getData();
        void abrirActivityPrincipal();
        void abrirActivitySignUp();
        void showText(String s);
    }

}
