package br.ufc.mobile.vendasfacil.ui;

import java.util.List;

import br.ufc.mobile.vendasfacil.model.UsuarioDTO;

public class VendasFacilView {

    public interface IShowText{
        void showText(String s);
    }

    public interface ViewMaster<T> extends IShowText{
        void updateAdapter(List<T> dados);
    }

    public interface ViewDetails<T> extends IShowText{
        T getData();
        void finishActivity();
    }

    public interface ViewLogin extends IShowText{
        UsuarioDTO getData();
        void abrirActivityPrincipal();
        void abrirActivitySignUp();
    }

}
