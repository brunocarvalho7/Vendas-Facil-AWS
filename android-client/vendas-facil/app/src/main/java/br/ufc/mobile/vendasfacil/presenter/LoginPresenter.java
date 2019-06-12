package br.ufc.mobile.vendasfacil.presenter;

import br.ufc.mobile.vendasfacil.model.Usuario;

public interface LoginPresenter {

    void signIn();

    void signUp();

    void performSignUp(Usuario usuario);
}
